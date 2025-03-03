package dnl.uxlc.xml.json.conversion

import dnl.bible.api.BibleBook
import dnl.bible.json.SerializableBible
import dnl.bible.json.SerializableBook
import dnl.bible.json.JsonPersistency
import dnl.uxlc.xml.xstream.OnlyHebrew
import dnl.uxlc.xml.xstream.OnlyHebrewLetters
import dnl.uxlc.xml.xstream.WordProcessingStack
import dnl.uxlc.xml.xstream.XStreamConversion
import net.java.truevfs.access.TFile
import net.java.truevfs.access.TVFS
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileReader

class XmlToJsonConversion(
    private val rootXmlDir: File,
    private val targetDir: File,
    private val fullBibleTargetBaseName:String) {

    private val wordProcessingStack = WordProcessingStack(OnlyHebrewLetters())
    private val wordWithNiqqudProcessingStack = WordProcessingStack(OnlyHebrew())

    private val logger = LoggerFactory.getLogger(javaClass)

    private fun getXmlFileForBook(bibleBook: BibleBook): File {
        rootXmlDir.listFiles()!!.forEach {
            if (it.name.contains(bibleBook.englishName.replace(' ', '_'), true))
                return it
        }
        return File("")
    }

    fun processFullBible() {
        logger.info("processFullBible(): target is $fullBibleTargetBaseName")
        val boox = mutableListOf<SerializableBook>()
        BibleBook.entries.forEach {
            val xmlFile = getXmlFileForBook(it)
            if (!xmlFile.exists()) {
                println("No file for $it")
                return@forEach
            }
            println("processing file: ${xmlFile.name}")
            val xmlParser = XStreamConversion(wordProcessingStack, wordWithNiqqudProcessingStack)
            val book = xmlParser.convert(FileReader(xmlFile), it) as SerializableBook
            boox.add(book)
        }

        val bible = SerializableBible(boox)
        val jsonPersistency = JsonPersistency()
        val jsonFile = File(targetDir, "$fullBibleTargetBaseName.json")
        jsonPersistency.write(jsonFile, bible)

        var archive = TFile(targetDir, "$fullBibleTargetBaseName.zip")
        archive.mkdir(false)
        if (archive.isDirectory) archive = TFile(archive, jsonFile.getName())
        TFile(jsonFile).cp_rp(archive)
        TVFS.umount(archive)
        logger.info("Done.")
    }

}

fun main() {
    val rootXmlDir = File("./uxlc-xml-json-conversion/xml-output/uxlc-1.2")
    val targetJsonDir = File("./uxlc-xml-json-conversion/json-output/uxlc-1.2")

    targetJsonDir.listFiles()!!.forEach { it.delete() }

    XmlToJsonConversion(
        rootXmlDir,
        targetJsonDir,
        "bible-1.2"
    ).processFullBible()
}

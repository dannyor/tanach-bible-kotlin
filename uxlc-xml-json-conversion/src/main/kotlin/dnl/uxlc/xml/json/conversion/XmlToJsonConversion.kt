package dnl.uxlc.xml.json.conversion

import dnl.bible.api.BibleBook
import dnl.bible.json.v2.Bible
import dnl.bible.json.v2.Book
import dnl.bible.json.v2.JsonPersistency
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
    val wordProcessingStack: WordProcessingStack,
    val rootXmlDir: File,
    val targetDir: File,
    val fullBibleTargetBaseName:String) {

    val logger = LoggerFactory.getLogger(javaClass)

    fun getXmlFileForBook(bibleBook: BibleBook): File {
        rootXmlDir.listFiles().forEach {
            if (it.name.contains(bibleBook.englishName.replace(' ', '_'), true))
                return it
        }
        return File("")
    }

    fun processFullBible() {
        logger.info("processFullBible(): target is $fullBibleTargetBaseName. wordProcessingStack=$wordProcessingStack")
        val boox = mutableListOf<Book>()
        BibleBook.values().forEach {
            val xmlFile = getXmlFileForBook(it)
            if (!xmlFile.exists()) {
                println("No file for $it")
                return@forEach
            }
            println("processing file: ${xmlFile.name}")
            val xmlParser = XStreamConversion(wordProcessingStack)
            val book = xmlParser.convert(FileReader(xmlFile), it) as Book
            boox.add(book)
        }

        val bible = Bible(boox)
        val jsonPersistency = JsonPersistency()
        val jsonFile = File(targetDir, "$fullBibleTargetBaseName.json")
        jsonPersistency.write(jsonFile, bible)

        var archive = TFile(targetDir, "$fullBibleTargetBaseName.zip")
        archive.mkdir(false)
        if (archive.isDirectory()) archive = TFile(archive, jsonFile.getName())
        TFile(jsonFile).cp_rp(archive)
        TVFS.umount(archive)
        logger.info("Done.")
    }

}

fun main() {
    val rootXmlDir = File("./uxlc-xml-json-conversion/xml-output/uxlc-1.2")
    val targetJsonDir = File("./uxlc-xml-json-conversion/json-output/uxlc-1.2")

    targetJsonDir.listFiles().forEach { it.delete() }

    XmlToJsonConversion(
        WordProcessingStack(OnlyHebrewLetters()),
        rootXmlDir,
        targetJsonDir,
        "bible-just_letters-1.1"
    ).processFullBible()

    XmlToJsonConversion(
        WordProcessingStack(OnlyHebrew()),
        rootXmlDir,
        targetJsonDir,
        "bible-nikud_and_teamim-1.1"
    ).processFullBible()

}

package dnl.bible.json

import kotlinx.serialization.json.Json
import org.apache.commons.io.IOUtils

private val json = Json { prettyPrint = true }

val bible = loadBible()

fun loadBible(): dnl.bible.api.Bible {
    val istream = ClassLoader.getSystemResourceAsStream("bible-1.2.json")
    istream.use {
        val fileContents = IOUtils.toString(istream, "UTF-16")
        val bible: SerializableBible = json.decodeFromString(fileContents)
        return BibleImpl(bible)
    }
}

//object BibleLoader {
//
//    private val json = Json { prettyPrint = true }
//
//    fun loadBible(bibleZipFile: File): dnl.bible.api.Bible {
//        if (!bibleZipFile.exists())
//            throw java.lang.IllegalArgumentException("File ${bibleZipFile.absolutePath} does not exist")
//        else if (bibleZipFile.name.endsWith(".zip")) {
//            val tFile = TFile(bibleZipFile).listFiles()!![0]
//            val istream = TFileInputStream(tFile)
//            istream.use {
//                val fileContents = IOUtils.toString(istream, "UTF-16")
//                val bible: SerializableBible = json.decodeFromString(fileContents)
//                return BibleImpl(bible)
//            }
//        }
//        throw IllegalArgumentException("Only json or zipped json are supported")
//    }
//}


package dnl.bible.json.v2

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.java.truevfs.access.TFile
import net.java.truevfs.access.TFileInputStream
import org.apache.commons.io.IOUtils
import java.io.File

object BibleLoader {

    fun loadJustLettersBible(bibleZipFile: File): Bible {
        return loadBible(bibleZipFile, true)
    }

    fun loadBibleWithNikudAndTeamim(bibleZipFile: File): Bible {
        return loadBible(bibleZipFile, false)
    }

    private fun loadBible(bibleZipFile: File, justLetters: Boolean): Bible {
        fun resolveBibleFile(justLetters: Boolean): TFile {
            TFile(bibleZipFile).listFiles()!!.forEach {
                if (justLetters && it.name.contains("just"))
                    return it
                if (!justLetters && it.name.contains("nikud"))
                    return it
            }
            throw IllegalStateException()
        }

        if (!bibleZipFile.exists())
            throw java.lang.IllegalArgumentException("File ${bibleZipFile.absolutePath} does not exist")
        else if (bibleZipFile.name.endsWith(".zip")) {
            val zipFile = TFile(bibleZipFile)
            val tFile = resolveBibleFile(justLetters)
            val istream = TFileInputStream(tFile)
            istream.use {
                val fileContents = IOUtils.toString(istream, "UTF-16")
                return Json { prettyPrint = true }.decodeFromString(fileContents)
            }
        }
        throw IllegalArgumentException("Only json or zipped json are supported")
    }
}


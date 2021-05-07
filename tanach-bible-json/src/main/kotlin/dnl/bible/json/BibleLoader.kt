package dnl.bible.json

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.java.truevfs.access.TFile
import net.java.truevfs.access.TFileInputStream
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File

object BibleLoader {
    fun loadFrom(f: File) : Bible {
        if(f.name.endsWith(".json")) {
            val fileContents = FileUtils.readFileToString(f, "UTF-16")
            return Json { prettyPrint = true }.decodeFromString(fileContents)
        }
        else if(f.name.endsWith(".zip")) {
            val zipFile = TFile(f)
            val tFile = zipFile.listFiles()!![0]
            val istream = TFileInputStream(tFile)
            istream.use {
                val fileContents = IOUtils.toString(istream, "UTF-16")
                return Json { prettyPrint = true }.decodeFromString(fileContents)
            }
        }
        throw IllegalArgumentException("Only json or zipped json are supported")
    }
}


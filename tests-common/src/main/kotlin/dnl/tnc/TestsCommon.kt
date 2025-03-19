package dnl.tnc

import dnl.bible.json.BibleLoader
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import java.io.File

val testsOutputDir = File("tests-output")

fun assertTestsOutputDirExists() {
    if (!testsOutputDir.exists()) {
        FileUtils.forceMkdir(testsOutputDir)
    }
}


val bible = BibleLoader.loadBible(
    File("../uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-1.2.zip")
)

val json = Json { prettyPrint = true }

fun toJson(any: Any): String {
    return json.encodeToString(any)
}
package dnl.tnc

import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import java.io.File

val testsOutputDir = File("tests-output")

fun assertTestsOutputDirExists() {
    if (!testsOutputDir.exists()) {
        FileUtils.forceMkdir(testsOutputDir)
    }
}

val json = Json { prettyPrint = true }

fun toJson(any: Any): String {
    return json.encodeToString(any)
}
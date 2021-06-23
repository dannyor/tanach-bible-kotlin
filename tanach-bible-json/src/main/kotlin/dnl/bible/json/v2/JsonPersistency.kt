package dnl.bible.json.v2

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import java.io.File

class JsonPersistency {
    fun write(file: File, bible: Bible) {
        val encodedString = Json { prettyPrint = true }.encodeToString(bible)
        FileUtils.write(file, encodedString, "UTF-16")
    }

    fun write(file: File, book: Book) {
        val encodedString = Json { prettyPrint = true }.encodeToString(book)
        FileUtils.write(file, encodedString, "UTF-16")
    }
}
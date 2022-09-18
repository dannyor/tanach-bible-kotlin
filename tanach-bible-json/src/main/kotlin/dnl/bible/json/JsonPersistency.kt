package dnl.bible.json

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import java.io.File

class JsonPersistency {
    fun write(file: File, bible: SerializableBible) {
        val encodedString = Json { prettyPrint = true }.encodeToString(bible)
        FileUtils.write(file, encodedString, "UTF-16")
    }

    fun write(file: File, book: SerializableBook) {
        val encodedString = Json { prettyPrint = true }.encodeToString(book)
        FileUtils.write(file, encodedString, "UTF-16")
    }
}
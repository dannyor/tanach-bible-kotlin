package dnl.bible.dictionary

import dnl.bible.CombinedBibleWordTraversal
import dnl.bible.api.BibleBook
import dnl.bible.api.HebrewNumberingSystem.Companion.toHebrewString
import dnl.bible.api.VerseLocation
import dnl.bible.json.BibleLoader
import dnl.bible.json.CombinedBible
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import java.io.File

class DictionaryCreator(bible: CombinedBible) : CombinedBibleWordTraversal(bible) {
    val map = mutableMapOf<String, MutableSet<WordData>>()

    override fun traverseWord(word: String, full: String, verseLocation: VerseLocation) {
        if (!map.containsKey(word)) {
            map[word] = mutableSetOf()
        }
        map[word]!!.add(WordData(full, verseLocation.toSerializableLocation()))
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun main() {
    val bible = BibleLoader.loadCombined(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip"),
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-nikud_and_teamim-1.1.zip")
    )
    val dictionaryCreator = DictionaryCreator(bible)
    dictionaryCreator.process()
    println(dictionaryCreator.map.size)
    val json = Json { prettyPrint = true }
    val encodedString = json.encodeToString(dictionaryCreator.map)
    FileUtils.write(File("products/dictionary.json"), encodedString, "UTF-16")
}

@Serializable
data class WordData(val f: String, val location: SerializableLocation)

@Serializable
data class SerializableLocation(val b: BibleBook, val c: Int, val v: Int) {
    fun toStringHeb(): String {
        return "[${b.hebrewName}, ${c.toHebrewString()}, ${v.toHebrewString()}]"
    }
}

fun VerseLocation.toSerializableLocation(): SerializableLocation =
    SerializableLocation(book, chapterIndex, verseIndex)

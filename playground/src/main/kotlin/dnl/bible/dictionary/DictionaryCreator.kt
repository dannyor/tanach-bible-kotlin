package dnl.bible.dictionary

import dnl.bible.BibleWordTraversal
import dnl.bible.api.Bible
import dnl.bible.api.BibleBook
import dnl.bible.api.HebrewNumberingSystem.toHebrewString
import dnl.bible.api.VerseLocation
import dnl.bible.json.BibleLoader
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import java.io.File

class DictionaryCreator(bible: Bible) : BibleWordTraversal(bible) {
    val map = mutableMapOf<String, MutableSet<WordData>>()
    override fun traverseWord(word: String, wordWithDiacritics: String, verseLocation: VerseLocation) {
        if (!map.containsKey(word)) {
            map[word] = mutableSetOf()
        }
        map[word]!!.add(WordData(wordWithDiacritics, verseLocation.toSerializableLocation()))
    }
}

fun main() {
    val bible =
        BibleLoader.loadBible(File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-1.2.zip"))

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

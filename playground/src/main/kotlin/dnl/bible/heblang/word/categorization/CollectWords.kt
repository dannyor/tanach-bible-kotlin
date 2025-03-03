package dnl.bible.heblang.word.categorization

import dnl.bible.BibleWordTraversal
import dnl.bible.api.Bible
import dnl.bible.api.HebrewCharacterUtils.Companion.isHebrewLetter
import dnl.bible.api.HebrewCharacterUtils.Companion.isHebrewPunctuation
import dnl.bible.api.VerseLocation
import dnl.bible.json.BibleLoader
import dnl.bible.json.SerializableWord
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import java.io.File

class WordCollector(bible: Bible) : BibleWordTraversal(bible) {
    val map = mutableSetOf<SerializableWord>()

    override fun traverseWord(word: String, wordWithDiacritics: String, verseLocation: VerseLocation) {
        //val text = wordWithNiqqud.filter { isHebrewLetter(it) }
        val withPunctuation: String = wordWithDiacritics.filter { isHebrewLetter(it) || isHebrewPunctuation(it) }
        map.add(SerializableWord(word, withPunctuation))
    }
}

private val json = Json { prettyPrint = true }

@ExperimentalSerializationApi
fun main() {
    val bible = BibleLoader.loadBible(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-1.2.zip")
    )
    val wordCollector = WordCollector(bible)
    wordCollector.process()
    println(wordCollector.map.size)
    val encodedString = json.encodeToString(wordCollector.map)
    FileUtils.write(File("products/bible-words.json"), encodedString, "UTF-16")
}

@Serializable
data class CollectedWord(val simpleText: String, val punctuatedText: String, val punctuationAndTeamim: String)
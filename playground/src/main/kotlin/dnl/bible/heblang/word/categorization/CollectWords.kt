package dnl.bible.heblang.word.categorization

import dnl.bible.CombinedBibleWordTraversal
import dnl.bible.api.HebrewCharacterUtils.Companion.isHebrewLetter
import dnl.bible.api.HebrewCharacterUtils.Companion.isHebrewPunctuation
import dnl.bible.api.VerseLocation
import dnl.bible.json.BibleLoader
import dnl.bible.json.CombinedBible
import dnl.bible.json.SerializableWord
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import java.io.File

class WordCollector(bible: CombinedBible) : CombinedBibleWordTraversal(bible) {
    val map = mutableSetOf<SerializableWord>()

    override fun traverseWord(word: String, full: String, verseLocation: VerseLocation) {
//        val text = full.filter { isHebrewLetter(it) }
        val withPunctuation: String = full.filter { isHebrewLetter(it) || isHebrewPunctuation(it) }
         map.add(SerializableWord(word, withPunctuation))
    }
}

private val json = Json { prettyPrint = true }

@ExperimentalSerializationApi
fun main() {
    val bible = BibleLoader.loadCombined(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip"),
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-nikud_and_teamim-1.1.zip")
    )
    val wordCollector = WordCollector(bible)
    wordCollector.process()
    println(wordCollector.map.size)
    val encodedString = json.encodeToString(wordCollector.map)
    FileUtils.write(File("products/bible-words.json"), encodedString, "UTF-16")
}

@Serializable
data class CollectedWord(val simpleText: String, val punctuatedText: String, val punctuationAndTeamim: String)
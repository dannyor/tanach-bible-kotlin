package dnl.bible.verse4name

import dnl.bible.BibleWordTraversal
import dnl.bible.api.Bible
import dnl.bible.api.Gematria
import dnl.bible.api.VerseLocation
import dnl.bible.api.toStringHeb
import dnl.bible.json.BibleLoader
import java.io.File

class WordGimatry(bible: Bible, rootWord: String) : BibleWordTraversal(bible) {
    private val _gematriaOfRootWord = Gematria.gematriaOf(rootWord)
    private val _results = mutableListOf<Pair<String, VerseLocation>>()

    override fun traverseWord(word: String, wordWithDiacritics: String, verseLocation: VerseLocation) {
        val g = Gematria.gematriaOf(word)
        if (g == _gematriaOfRootWord) {
            _results.add(Pair(word, verseLocation))
        }
    }

    fun getResults(): List<Pair<String, VerseLocation>> = _results
}

fun main() {
    val bible = BibleLoader.loadBible(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-1.2.zip")
    )
    val wordGimatry = WordGimatry(bible, "דניאל")
    wordGimatry.process()
    wordGimatry.getResults().forEach {
        println("${it.first} - ${it.second.toStringHeb()}: \"${bible.getVerse(it.second).getText()}\"")
    }
}
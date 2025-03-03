package dnl.bible.verse4name

import dnl.bible.BibleWordTraversalWithResults
import dnl.bible.api.Bible
import dnl.bible.api.Gematria
import dnl.bible.api.VerseLocation
import dnl.bible.api.toStringHeb
import dnl.bible.json.BibleLoader
import java.io.File

class WordGimatry(bible: Bible, rootWord: String) : BibleWordTraversalWithResults(bible) {
    private val _gematriaOfRootWord = Gematria.gematriaOf(rootWord)

    override fun traverseWord(word: String, wordWithDiacritics: String, verseLocation: VerseLocation):Boolean {
        return Gematria.gematriaOf(word) == _gematriaOfRootWord
    }
}

fun main() {
    val bible = BibleLoader.loadBible(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-1.2.zip")
    )
    val wordGimatry = WordGimatry(bible, "דניאל")
    wordGimatry.process()
    wordGimatry.results.forEach {
        println("${it.word} - ${it.verseLocation.toStringHeb()}: \"${bible.getVerse(it.verseLocation).getText()}\"")
    }
}



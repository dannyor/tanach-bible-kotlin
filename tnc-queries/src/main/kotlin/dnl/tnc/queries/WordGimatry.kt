package dnl.tnc.queries

import dnl.bible.api.Bible
import dnl.bible.api.Gematria
import dnl.bible.api.toStringHeb
import dnl.bible.json.BibleLoader
import java.io.File


fun queryForSameGimatria(bible: Bible, rootWord: String): List<WordResult> {
    val gematriaOfRootWord = Gematria.gematriaOf(rootWord)
    val visitor = GenericVisitorWithResults { word: String -> Gematria.gematriaOf(word) == gematriaOfRootWord }
    BibleWordTraversal(bible).traverse(visitor)
    return visitor.results
}

fun main() {
    val bible = BibleLoader.loadBible(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-1.2.zip")
    )
    val results = queryForSameGimatria(bible, "אילת")

    results.forEach {
        println("${it.word} - ${it.wordLocation.toStringHeb()}: \"${bible.getVerse(it.wordLocation).getText()}\"")
    }
}
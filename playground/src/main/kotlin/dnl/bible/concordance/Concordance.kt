@file:Suppress("MemberVisibilityCanBePrivate")

package dnl.bible.concordance

import dnl.bible.api.BibleBook
import dnl.bible.api.v2.Bible
import dnl.bible.api.v2.Verse
import dnl.bible.api.v2.VerseLocation
import dnl.bible.json.v2.BibleLoader
import org.slf4j.LoggerFactory
import java.io.File

interface Concordance {
    fun find(word:String) : List<Verse>
}

class ConcordanceFromBible(val bible: Bible): Concordance {
    val logger = LoggerFactory.getLogger(javaClass)

    override fun find(word: String): List<Verse> {
        logger.info("Find '$word'")

        val result = mutableListOf<Verse>()

        BibleBook.values().forEach { book ->
            logger.info("start processing $book")
            val bookResult = mutableListOf<Verse>()
            var iterator = bible.getBook(book).getIterator()
            while (iterator.hasNext()) {
                val verse = iterator.next()
                if(verse.getText().contains(word))
                    bookResult.add(verse)
            }
            result.addAll(bookResult)
            logger.info("Found ${bookResult.size} results i book $book")
        }
        return result
    }

}

fun main() {
    val bible = BibleLoader.loadJustLettersBible(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip"))
    val concordance = ConcordanceFromBible(bible)
    val result = concordance.find("אהבה")
    result.forEach {
        println("${it.getText()} ${it.getLocation().toStringHeb()}")
    }
}
@file:Suppress("MemberVisibilityCanBePrivate")

package dnl.bible.concordance

import dnl.bible.api.Bible
import dnl.bible.api.BibleBook
import dnl.bible.api.Verse
import dnl.bible.api.toStringHeb
import dnl.bible.json.bible
import org.slf4j.LoggerFactory

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
    val concordance = ConcordanceFromBible(bible)
    val result = concordance.find("אהבה")
    result.forEach {
        println("${it.getText()} ${it.getLocation().toStringHeb()}")
    }
}
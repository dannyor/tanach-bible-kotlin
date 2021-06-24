@file:Suppress("MemberVisibilityCanBePrivate")

package dnl.bible.concordance

import dnl.bible.api.Bible
import dnl.bible.api.BibleBook
import dnl.bible.api.Verse
import dnl.bible.api.VerseLocation
import dnl.bible.api.v2.Bible
import dnl.bible.api.v2.VerseLocation
import dnl.bible.json.BibleLoader
import org.slf4j.LoggerFactory
import java.io.File

interface Concordance {
    fun find(word:String) : List<VerseLocation>
}

class ConcordanceFromBible(val bible: Bible): Concordance {
    val logger = LoggerFactory.getLogger(javaClass)

    override fun find(word: String): List<VerseLocation> {
        logger.info("Find '$word'")

        val result = mutableListOf<VerseLocation>()

        BibleBook.values().forEach {
            logger.info("start processing $it")
            val bookResult = mutableListOf<VerseLocation>()
            var verse = bible.getBook(it).getVerse(VerseLocation(it, 1, 1))
            while (verse.hasNext()) {
                if(verse.getText().contains(word))
                    bookResult.add(verse)
                verse = verse.next()
            }
            result.addAll(bookResult)
            logger.info("Found ${bookResult.size} results i book $it")
        }
        return result
    }

}

fun main() {
    val bible = BibleLoader.loadFrom(File("../json-bible-hebrew/bible-json-files/bible-just_letters-1.0.zip"))
    val concordance = ConcordanceFromBible(bible)
    val result = concordance.find("אהבה")
    result.forEach {
        println("${it.getText()} ${it.getLocation().toStringHeb()}")
    }
}
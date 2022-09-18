package dnl.bible

import dnl.bible.api.Bible
import dnl.bible.api.BibleBook
import dnl.bible.api.VerseLocation
import dnl.bible.json.CombinedBible
import dnl.bible.json.CombinedVerse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BibleWordTraversal(val bible: Bible) {
    val logger = LoggerFactory.getLogger(javaClass)

    fun process() {
        logger.info("process() started")
        BibleBook.values().forEach { bibleBook ->
            logger.info("process()ing $bibleBook")
            bible.getBook(bibleBook).getIterator().forEach { verse ->
                verse.getWords().forEach { traverseWord(it, verse.getLocation()) }
            }
        }
    }

    abstract fun traverseWord(word:String, verseLocation: VerseLocation)
}

abstract class CombinedBibleWordTraversal(val bible: CombinedBible) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun process() {
        logger.info("process() started")
        BibleBook.values().forEach { bibleBook ->
            logger.info("process()ing $bibleBook")
            bible.getBook(bibleBook).getIterator().forEach { verse ->
                val cv = verse as CombinedVerse
                cv.getCombinedWords().forEach { traverseWord(it.first, it.second, cv.getLocation()) }
            }
        }
    }

    abstract fun traverseWord(word:String, full:String, verseLocation: VerseLocation)
}
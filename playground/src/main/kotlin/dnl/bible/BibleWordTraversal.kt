package dnl.bible

import dnl.bible.api.Bible
import dnl.bible.api.BibleBook
import dnl.bible.api.TextDirective
import dnl.bible.api.VerseLocation
import org.slf4j.LoggerFactory

data class TraversalResult(
    val word: String,
    val wordWithDiacritics: String,
    val verseLocation: VerseLocation
)

abstract class BibleWordTraversal(val bible: Bible) {
    private val logger = LoggerFactory.getLogger(javaClass)
    val results = mutableListOf<TraversalResult>()

    fun process() {
        logger.info("process() started")
        BibleBook.entries.forEach { bibleBook ->
            logger.info("process()ing $bibleBook")
            bible.getBook(bibleBook).getIterator().forEach { verse ->
                val words = verse.getWords(TextDirective.SIMPLE)
                val wordsWithDiacritics = verse.getWords(TextDirective.DIACRITICS)
                for (i in words.indices) {
                    traverseWord(words[i], wordsWithDiacritics[i], verse.getLocation())
                }
            }
        }
    }

    abstract fun traverseWord(word: String, wordWithDiacritics: String, verseLocation: VerseLocation)
}

abstract class BibleWordTraversalWithResults(val bible: Bible) {
    private val logger = LoggerFactory.getLogger(javaClass)
    val results = mutableListOf<TraversalResult>()

    fun process() {
        logger.info("process() started")
        BibleBook.entries.forEach { bibleBook ->
            logger.info("process()ing $bibleBook")
            bible.getBook(bibleBook).getIterator().forEach { verse ->
                val words = verse.getWords(TextDirective.SIMPLE)
                val wordsWithDiacritics = verse.getWords(TextDirective.DIACRITICS)
                for (i in words.indices) {
                    if (traverseWord(words[i], wordsWithDiacritics[i], verse.getLocation())) {
                        results.add(TraversalResult(words[i], wordsWithDiacritics[i], verse.getLocation()))
                    }
                }
            }
        }
    }

    abstract fun traverseWord(word: String, wordWithDiacritics: String, verseLocation: VerseLocation): Boolean
}
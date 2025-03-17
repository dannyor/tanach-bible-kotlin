package dnl.bible.json

import dnl.bible.api.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class VerseIterationTest {

    val bible = BibleLoader.loadBible(
        File("../uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-1.2.zip"))

    @Test
    fun testIterationSameChapter() {
        val book = bible.getBook(BibleBook.DANIEL)
        val range = VerseRangeFactoryImpl.newSingleChapterRange(book, 1)

    }

    @Test
    fun testBookIterator() {
        val verseIterator = bible.getBook(BibleBook.DANIEL).verseIterator()
        val verses = verseIterator.asList()
        Assertions.assertEquals("ואתה לך לקץ ותנוח ותעמד לגרלך לקץ הימין", verses.last().getText())
    }

    @Test
    fun testBibleVerseIterator() {
        val verseIterator = bible.verseIterator()
        while (verseIterator.hasNext()) {
            val verse = verseIterator.next()
            println(verse.getText())
        }
    }
    @Test
    fun testBibleWordIterator() {
        val wordIterator = bible.verseIterator().toWordIterator()
        while (wordIterator.hasNext()) {
            val word = wordIterator.next()
            println(word)
        }
    }

    @Test
    fun testSingleChapterWordIterator() {
        val wordIt = bible.getBook(BibleBook.GENESIS).getChapter(1).verseIterator().toWordIterator()
        while(wordIt.hasNext()){
            println(wordIt.next())
        }
    }

    @Test
    fun testSingleBookWordIterator() {
        val wordIt = bible.getBook(BibleBook.GENESIS).verseIterator().toWordIterator()
        while(wordIt.hasNext()){
            println(wordIt.next())
        }
    }

    @Test
    fun testBibleBookIterator() {
        val bookIterator = bible.bookIterator()
        while (bookIterator.hasNext()) {
            val book = bookIterator.next()
            println(book.getName())
        }
    }
}
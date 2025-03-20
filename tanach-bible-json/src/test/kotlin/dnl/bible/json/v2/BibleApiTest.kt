package dnl.bible.json.v2

import dnl.bible.api.BibleBook
import dnl.bible.api.VerseRangeFactory
import dnl.bible.json.bible
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BibleApiTest {
    @Test
    fun testBibleBookEnum() {
        val bbook = BibleBook.byEnglishName("Numbers")
        assertEquals(BibleBook.NUMBERS, bbook)
    }

    @Test
    fun testChapterRange() {
        val book = bible.getBook(BibleBook.DANIEL)
        val range = VerseRangeFactory.newSingleChapterRange(book, 1)
        val iterator = bible.getVerseRange(range)
        val verseList = iterator.asSequence().toList()
        assertEquals(21, verseList.size)
        assertEquals("ויהי דניאל עד שנת אחת לכורש המלך", verseList.last().getText())
    }

    @Test
    fun testDirectAccess() {
        val bookOfNumbers = bible.getBook(BibleBook.NUMBERS)
        assertEquals("Numbers", bookOfNumbers.getName())
        val chapter5 = bookOfNumbers.getChapter(5)
        assertEquals("Numbers", chapter5.getParent().getName())
        assertEquals(5, chapter5.getIndex())
        val verse1 = chapter5.getVerse(1)
        val expected = "וידבר יהוה אל משה לאמר"
        assertEquals(expected, verse1.getText())
    }

    @Test
    fun testWithRangeIterator() {
        val iterator = bible.getVerseRange(VerseRangeFactory.newVerseRange("Numbers 1:1-1:5"))
        val verses = iterator.asSequence().toList()
        assertEquals(1, verses[0].getParent().getIndex())
        assertEquals(1, verses[0].getIndex())
        assertEquals(
            "וידבר יהוה אל משה במדבר סיני באהל מועד באחד לחדש השני בשנה השנית לצאתם מארץ מצרים לאמר",
            verses[0].getText()
        )

        assertEquals(1, verses[4].getParent().getIndex())
        assertEquals(5, verses[4].getIndex())
    }
}
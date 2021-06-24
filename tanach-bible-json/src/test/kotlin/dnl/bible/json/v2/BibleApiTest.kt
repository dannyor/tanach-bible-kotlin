package dnl.bible.json.v2

import dnl.bible.api.BibleBook
import dnl.bible.json.VerseIterationTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File

class BibleApiTest {
    companion object {
        lateinit var bible: Bible
        @BeforeAll @JvmStatic fun setup() {
            bible = BibleLoader.loadJustLettersBible(
                File("../uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip"))
        }
    }

    @Test
    fun testBibleBookEnum() {
        val bbook = BibleBook.byEnglishName("Numbers")
        assertEquals(BibleBook.NUMBERS, bbook)
    }

    @Test
    fun testChapterRange() {
//        val chapter = bible.getBook(BibleBook.DANIEL).getChapter(1)
//        val verseList = chapter.iterator().asSequence().toList()
//        assertEquals(20, verseList.size)
    }

    @Test
    fun testDirectAccess() {
        val bookOfNumbers = bible.getBook(BibleBook.NUMBERS)
        assertEquals("Numbers", bookOfNumbers.name)
        val chapter5 = bookOfNumbers.getChapter(5)
        assertEquals("Numbers", chapter5.getParent().name)
//        assertEquals(5, chapter5.getIndex())
        val verse1 = chapter5.verses[1]
        val expected = "וידבר יהוה אל משה לאמר"
        assertEquals(expected, verse1)
    }

//    @Test
//    fun testWithRangeIterator() {
//        val iterator = bible.getVerseRange(VerseRangeFactory.newVerseRange("Numbers 1:1-1:5"))
//        val verses = iterator.asSequence().toList()
//        assertEquals(1, verses[0].getParent().getIndex())
//        assertEquals(1, verses[0].getIndex())
//        assertEquals("וידבר יהוה אל משה במדבר סיני באהל מועד באחד לחדש השני בשנה השנית לצאתם מארץ מצרים לאמר", verses[0].getText())
//
//        assertEquals(1, verses[4].getParent().getIndex())
//        assertEquals(5, verses[4].getIndex())
//    }
}
package dnl.bible.json

import dnl.bible.api.BibleBook
import dnl.bible.api.VerseRangeFactory
import dnl.bible.api.Bible
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File

class VerseIterationTest {
    companion object {
        lateinit var bible: Bible
        @BeforeAll
        @JvmStatic fun setup() {
            bible = BibleLoader.loadJustLettersBible(
                File("../uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip"))
        }
    }

    @Test
    fun testIterationSameChapter() {
        val book = bible.getBook(BibleBook.DANIEL)
        val range = VerseRangeFactory.newSingleChapterRange(book, 1)

    }
}
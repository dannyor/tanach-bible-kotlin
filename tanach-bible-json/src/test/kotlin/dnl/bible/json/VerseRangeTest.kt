package dnl.bible.json

import dnl.bible.api.BibleBook
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class VerseRangeTest {

    @Test
    fun testVerseRangeFactory() {
        val range = VerseRangeFactoryImpl.newVerseRange("Numbers 1:1-1:5")
        Assertions.assertEquals(BibleBook.NUMBERS, range.start.book)
        Assertions.assertEquals(1, range.start.chapterIndex)
        Assertions.assertEquals(1, range.start.verseIndex)
        Assertions.assertEquals(BibleBook.NUMBERS, range.end.book)
        Assertions.assertEquals(1, range.end.chapterIndex)
        Assertions.assertEquals(5, range.end.verseIndex)
    }

}
package dnl.bible.api

import dnl.bible.api.v2.Book
import dnl.bible.api.v2.VerseLocation
import dnl.bible.api.v2.VerseRange

class VerseRangeIterator(private val book: Book, private val range: VerseRange) : Iterator<String> {
    private lateinit var currentLocation: VerseLocation

    override fun hasNext(): Boolean {
        if (!this::currentLocation.isInitialized) return true
        if (currentLocation.chapterIndex == range.end.chapterIndex && currentLocation.verseIndex == range.end.verseIndex)
            return false
        if (currentLocation.chapterIndex < 3/*book.getNumOfChapters()*/ ||
            currentLocation.verseIndex < book.getChapter(currentLocation.chapterIndex).verses.size
        )
            return true
        return true
    }

    override fun next(): String {
        currentLocation =
            if (!this::currentLocation.isInitialized)
                VerseLocation(book.bookEnumVal, 1, 1)
            else {
                // last verse in chapter
                if (currentLocation.verseIndex == book.getChapter(currentLocation.chapterIndex).verses.size) {
                    VerseLocation(book.bookEnumVal, currentLocation.chapterIndex + 1, 1)
                }
                VerseLocation(book.bookEnumVal, currentLocation.chapterIndex, currentLocation.verseIndex + 1)
            }
        return book.getChapter(currentLocation.chapterIndex - 1).verses[currentLocation.verseIndex - 1]
    }
}

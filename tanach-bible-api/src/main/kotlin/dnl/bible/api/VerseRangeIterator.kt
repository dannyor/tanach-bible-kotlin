package dnl.bible.api

import dnl.bible.api.v2.Book
import dnl.bible.api.v2.Verse
import dnl.bible.api.v2.VerseRange

class VerseRangeIterator(private val book: Book, private val range: VerseRange) : Iterator<Verse> {
    private lateinit var current: Verse

    override fun hasNext(): Boolean {
        if (!this::current.isInitialized) return true
        if (current.getParent().getIndex() == range.end.chapterIndex && current.getIndex() == range.end.verseIndex)
            return false
        return true
    }

    override fun next(): Verse {
        current = if (!this::current.isInitialized) {
            book.getVerse(range.start)
        } else {
            if (isAtChapterEnd()) {
                // first verse of next chapter
                current.getParentBook().getChapter(current.getParent().getIndex() + 1).getVerse(1)
            } else {
                current.getParent().getVerse(current.getIndex()+1)
            }
        }
        return current
    }

    private fun isAtChapterEnd(): Boolean {
        return current.getLocation().verseIndex == current.getParent().getNumOfVerses()
    }
}
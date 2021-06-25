package dnl.bible.api

import dnl.bible.api.BibleBook
import dnl.bible.api.HebrewNumberingSystem.Companion.toHebrewString
import dnl.bible.api.HumashEnum
import dnl.bible.api.ParashaEnum
import dnl.bible.api.VerseRangeIterator

interface Tora {
    fun getHumash(humash: HumashEnum): Book
    fun getParasha(parasha: ParashaEnum): Parasha
}

interface Parasha {
    val name: String
    fun getVerses(): List<Verse>
}


interface Bible {
    fun getBook(book: BibleBook): Book
    fun getVerse(verseLocation: VerseLocation): Verse {
        return getBook(verseLocation.book).getChapter(verseLocation.chapterIndex).getVerse(verseLocation.verseIndex - 1)
    }

    fun getVerseRange(verseRange: VerseRange): Iterator<Verse> {
        return VerseRangeIterator(getBook(verseRange.start.book), verseRange)
    }
}

/**
 * Note that bible indexes are 1 based.
 */
interface Book {
    fun getName(): String
    fun getHebrewName(): String
    fun getBookEnum(): BibleBook
    fun getNumOfChapters(): Int

    /**
     * Gets a chapter by a (1 based) index. That is, first chapter index is 1.
     */
    fun getChapter(index: Int): Chapter

    fun getVerse(location: VerseLocation): Verse {
        return getChapter(location.chapterIndex).getVerse(location.verseIndex)
    }

    fun getVerseRange(verseRange: VerseRange): Iterator<Verse> {
        return VerseRangeIterator(this, verseRange)
    }

    /**
     * Gets the whole book as VerseRange
     */
    fun asVerseRange(): VerseRange {
        val firstVerse = VerseLocation(getBookEnum(), 1, 1)
        val lastVerse = VerseLocation(getBookEnum(), getNumOfChapters(), lastChapter().getNumOfVerses())
        return VerseRange(firstVerse, lastVerse)
    }

    fun getIterator(): Iterator<Verse> {
        return VerseRangeIterator(this, asVerseRange())
    }

    fun lastChapter(): Chapter = getChapter(getNumOfChapters())
}

interface Humash : Book {

}

interface Chapter {
    fun getParent(): Book
    fun getIndex(): Int
    fun getNumOfVerses(): Int
    fun getAllVerses(): List<Verse>
    fun getAllVersesStrings(): List<String>
    fun getVerse(index: Int): Verse

    fun asVerseRange(): VerseRange {
        val firstVerse = VerseLocation(getParent().getBookEnum(), getIndex(), 1)
        val lastVerse = VerseLocation(getParent().getBookEnum(), getIndex(), getNumOfVerses())
        return VerseRange(firstVerse, lastVerse)
    }

    fun getIterator(): Iterator<Verse> = VerseRangeIterator(getParent(), asVerseRange())
}

interface Verse {
    fun getParent(): Chapter
    fun getParentBook(): Book = getParent().getParent()
    fun getIndex(): Int
    fun getWords(): List<String>
    fun getText(): String

    fun getLocation(): VerseLocation {
        return VerseLocation(getParent().getParent().getBookEnum(), getParent().getIndex(), getIndex())
    }
}

data class VerseLocation(val book: BibleBook, val chapterIndex: Int, val verseIndex: Int) {
    fun toStringHeb(): String {
        return "[${book.hebrewName}, ${chapterIndex.toHebrewString()}, ${verseIndex.toHebrewString()}]"
    }
}

/**
 * Describes an inclusive range of verses.
 */
data class VerseRange(val start: VerseLocation, val end: VerseLocation) {
    init {
        if (start.book != end.book)
            throw IllegalArgumentException("Ranges are allowed only on the same book")
    }
}


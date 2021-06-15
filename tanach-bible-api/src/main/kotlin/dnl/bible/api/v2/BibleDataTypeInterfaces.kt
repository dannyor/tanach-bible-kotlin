package dnl.bible.api.v2

import dnl.bible.api.BibleBook
import dnl.bible.api.HumashEnum
import dnl.bible.api.ParashaEnum

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
        return getBook(verseLocation.book).getChapter(verseLocation.chapterIndex).verses[verseLocation.verseIndex-1]
    }
//    fun getVerseRange(verseRange: VerseRange): Iterator<Verse> {
//        return VerseRangeIterator(getBook(verseRange.start.book), verseRange)
//    }
}

interface Book {
    val name: String
    val hebrewName: String
    val numOfChapters: Int
    val bookEnumVal : BibleBook

    /**
     * Gets a chapter by a (1 based) index. That is, first chapter index is 1.
     */
    fun getChapter(index: Int): Chapter

    fun getVerse(location: VerseLocation): Verse {
        return getChapter(location.chapterIndex).verses[location.verseIndex-1]
    }

//    fun getVerseRange(verseRange: VerseRange): Iterator<Verse> {
//        return VerseRangeIterator(this, verseRange)
//    }
}

interface Humash : Book {

}

interface Chapter {
    val verses: List<Verse>
    fun getParent(): Book
    fun getIndex(): Int

    /**
     * Gets a verse by a (1 based) index. That is, first verse index is 1.
     */
//    fun getVerse(index: Int): Verse
//    fun getVerseAsString(index: Int): String

}

interface Verse {
    fun getParent(): Chapter
    fun getIndex(): Int
    val words: List<String>
    fun getText(): String
    fun getLocation(): VerseLocation {
        return VerseLocation(getParent().getParent().bookEnumVal, getParent().getIndex(), getIndex())
    }
}

data class VerseLocation(val book: BibleBook, val chapterIndex: Int, val verseIndex: Int) {
    fun toStringHeb() : String {
        return "[${book.hebrewName}, $chapterIndex, $verseIndex]"
    }
}

/**
 * Describes an inclusive range of verses.
 */
data class VerseRange(val start: VerseLocation, val end: VerseLocation) {
    init {
        if(start.book != end.book)
            throw IllegalArgumentException("Ranges are allowed only on the same book")
    }
}


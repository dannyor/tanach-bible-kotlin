package dnl.bible.json

import dnl.bible.api.BibleBook
import dnl.bible.api.VerseLocation
import dnl.bible.api.VerseRange
import dnl.bible.api.WordLocation

internal data class BasicVerseLocation(
    override val book: BibleBook,
    override val chapterIndex: Int,
    override val verseIndex: Int
) : VerseLocation {
    init {
        require(verseIndex > 0)
    }
}

internal data class BasicWordLocation(
    override val book: BibleBook,
    override val chapterIndex: Int,
    override val verseIndex: Int,
    override val wordIndex: Int
) : WordLocation {
    init {
        require(verseIndex > 0)
    }
}

/**
 * Describes an inclusive range of verses.
 */
data class VerseRangeImpl(
    override val start: VerseLocation,
    override val end: VerseLocation
) : VerseRange {
    init {
        if (start.book != end.book)
            throw IllegalArgumentException("Ranges are allowed only on the same book")
    }
}

fun VerseLocation.wordLocation(wordIndex: Int): WordLocation {
    return Locations.newWordLocation(this, wordIndex)
}

object Locations {

    fun newVerseLocation(book: BibleBook, chapterIndex: Int, verseIndex: Int): VerseLocation {
        return BasicVerseLocation(book, chapterIndex, verseIndex)
    }

    fun newWordLocation(book: BibleBook, chapterIndex: Int, verseIndex: Int, wordIndex: Int): WordLocation {
        return BasicWordLocation(book, chapterIndex, verseIndex, wordIndex)
    }

    fun newWordLocation(verseLocation: VerseLocation, wordIndex: Int): WordLocation {
        return BasicWordLocation(verseLocation.book, verseLocation.chapterIndex, verseLocation.verseIndex, wordIndex)
    }
}
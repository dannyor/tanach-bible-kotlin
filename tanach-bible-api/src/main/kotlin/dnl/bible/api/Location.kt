package dnl.bible.api

import dnl.bible.api.HebrewNumberingSystem.toHebrewString


interface VerseLocation {
    val book: BibleBook
    val chapterIndex: Int
    val verseIndex: Int
}

interface WordLocation : VerseLocation {
    val wordIndex: Int
}

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
data class VerseRange(val start: VerseLocation, val end: VerseLocation) {
    init {
        if (start.book != end.book)
            throw IllegalArgumentException("Ranges are allowed only on the same book")
    }
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

fun VerseLocation.wordLocation(wordIndex: Int): WordLocation {
    return Locations.newWordLocation(this, wordIndex)
}

fun VerseLocation.toStringHeb() =
    "[${book.hebrewName}:${chapterIndex.toHebrewString()}:${verseIndex.toHebrewString()}]"

//fun WordLocation.toStringEng() =
//    "[${book.englishName}:${chapterIndex}:${verseIndex}:${wordIndex}]"

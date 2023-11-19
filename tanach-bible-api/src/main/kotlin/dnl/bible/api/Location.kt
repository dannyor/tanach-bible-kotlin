package dnl.bible.api

import dnl.bible.api.HebrewNumberingSystem.Companion.toHebrewString

data class VerseLocation(val book: BibleBook, val chapterIndex: Int, val verseIndex: Int)
data class WordLocation(val verseLocation: VerseLocation, val wordIndex: Int)

/**
 * Describes an inclusive range of verses.
 */
data class VerseRange(val start: VerseLocation, val end: VerseLocation) {
    init {
        if (start.book != end.book)
            throw IllegalArgumentException("Ranges are allowed only on the same book")
    }
}

fun VerseLocation.toStringHeb() =
    "[${book.hebrewName}:${chapterIndex.toHebrewString()}:${verseIndex.toHebrewString()}]"

fun WordLocation.toStringEng()=
    "[${verseLocation.book.englishName}:${verseLocation.chapterIndex}:${verseLocation.verseIndex}:${wordIndex}]"

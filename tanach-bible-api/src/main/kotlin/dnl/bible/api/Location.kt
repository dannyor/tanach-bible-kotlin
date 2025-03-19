package dnl.bible.api

import dnl.bible.api.HebrewNumberingSystem.toHebrewString
import kotlinx.serialization.Serializable


@Serializable
data class VerseLocation(
    val book: BibleBook,
    val chapterIndex: Int,
    val verseIndex: Int
) {
    init {
        require(verseIndex > 0)
    }

    fun wordLocation(wordIndex: Int) = WordLocation(book, chapterIndex, verseIndex, wordIndex)
}

interface VerseInnerLocation {
    val wordIndex: Int
    val charIndex: Int
}

interface VerseWithCharLocations {
    val verseLocation: VerseLocation
    val innerLocations: List<VerseInnerLocation>
}

@Serializable
data class WordLocation(
    val verseLocation: VerseLocation,
    val wordIndex: Int
) {
    constructor(
        book: BibleBook,
        chapterIndex: Int,
        verseIndex: Int,
        wordIndex: Int
    ) : this(VerseLocation(book, chapterIndex, verseIndex), wordIndex)
}

@Serializable
data class VerseRange(
    val start: VerseLocation,
    val end: VerseLocation
) {
    init {
        if (start.book != end.book)
            throw IllegalArgumentException("Ranges are allowed only on the same book")
    }
}

fun VerseLocation.toStringHeb() =
    "[${book.hebrewName}:${chapterIndex.toHebrewString()}:${verseIndex.toHebrewString()}]"

//fun WordLocation.toStringEng() =
//    "[${book.englishName}:${chapterIndex}:${verseIndex}:${wordIndex}]"

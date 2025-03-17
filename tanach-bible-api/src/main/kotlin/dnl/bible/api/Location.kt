package dnl.bible.api

import dnl.bible.api.HebrewNumberingSystem.toHebrewString


interface VerseLocation {
    val book: BibleBook
    val chapterIndex: Int
    val verseIndex: Int
}

interface VerseInnerLocation {
    val wordIndex: Int
    val charIndex: Int
}

interface VerseWithCharLocations {
    val verseLocation: VerseLocation
    val innerLocations: List<VerseInnerLocation>
}

interface WordLocation : VerseLocation {
    val wordIndex: Int
}

interface VerseRange {
    val start: VerseLocation
    val end: VerseLocation
}

interface VerseRangeFactory {
    fun newVerseRange(str: String): VerseRange
    fun newSingleChapterRange(book: Book, chapterIndex: Int): VerseRange
}

object DelegatingRangeFactory : VerseRangeFactory {
    lateinit var delegate: VerseRangeFactory
    override fun newVerseRange(str: String) = delegate.newVerseRange(str)

    override fun newSingleChapterRange(book: Book, chapterIndex: Int) =
        delegate.newSingleChapterRange(book, chapterIndex)
}


fun VerseLocation.toStringHeb() =
    "[${book.hebrewName}:${chapterIndex.toHebrewString()}:${verseIndex.toHebrewString()}]"

//fun WordLocation.toStringEng() =
//    "[${book.englishName}:${chapterIndex}:${verseIndex}:${wordIndex}]"

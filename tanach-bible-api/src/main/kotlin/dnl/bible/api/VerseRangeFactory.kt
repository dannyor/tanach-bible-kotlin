package dnl.bible.api

import java.util.regex.Pattern

object VerseRangeFactory {
    private val rangePattern = Pattern.compile("(\\w+) (\\d+):(\\d+).+?(\\d+):(\\d+)")

    fun newVerseRange(str: String): VerseRange {
        val matcher = rangePattern.matcher(str)
        matcher.find()
        val book = BibleBook.byEnglishName(matcher.group(1))
        return VerseRange(
            VerseLocation(book, matcher.group(2).toInt(), matcher.group(3).toInt()),
            VerseLocation(book, matcher.group(4).toInt(), matcher.group(5).toInt())
        )
    }

    fun newSingleChapterRange(book: Book, chapterIndex: Int): VerseRange {
        return VerseRange(
            VerseLocation(book.getBookEnum(), chapterIndex, 1),
            VerseLocation(book.getBookEnum(), chapterIndex, book.getChapter(chapterIndex).getNumOfVerses()),
        )
    }
}
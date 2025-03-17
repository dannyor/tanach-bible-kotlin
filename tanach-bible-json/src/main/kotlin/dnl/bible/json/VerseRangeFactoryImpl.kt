package dnl.bible.json

import dnl.bible.api.*
import dnl.bible.json.Locations.newVerseLocation
import java.util.regex.Pattern

object VerseRangeFactoryImpl : VerseRangeFactory {
    private val rangePattern = Pattern.compile("(\\w+) (\\d+):(\\d+).+?(\\d+):(\\d+)")

    init {
        DelegatingRangeFactory.delegate = this
    }

    override fun newVerseRange(str: String): VerseRange {
        val matcher = rangePattern.matcher(str)
        matcher.find()
        val book = BibleBook.byEnglishName(matcher.group(1))
        return VerseRangeImpl(
            newVerseLocation(book, matcher.group(2).toInt(), matcher.group(3).toInt()),
            newVerseLocation(book, matcher.group(4).toInt(), matcher.group(5).toInt())
        )
    }

    override fun newSingleChapterRange(book: Book, chapterIndex: Int): VerseRange {
        return VerseRangeImpl(
            newVerseLocation(book.getBookEnum(), chapterIndex, 1),
            newVerseLocation(book.getBookEnum(), chapterIndex, book.getChapter(chapterIndex).getNumOfVerses()),
        )
    }
}
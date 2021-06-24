package dnl.bible.json

import dnl.bible.api.BibleBook
import dnl.bible.api.v2.*

class BibleImpl(val delegate:dnl.bible.json.v2.Bible) : Bible {
    override fun getBook(book: BibleBook): Book {
        return BookImpl(delegate.getBook(book))
    }
}

class BookImpl(val delegate: dnl.bible.json.v2.Book) : Book {
    override fun getName(): String {
        return delegate.name
    }

    override fun getHebrewName(): String {
        return delegate.hebrewName
    }

    override fun getBookEnum(): BibleBook {
        return delegate.bookEnumVal
    }

    override fun getNumOfChapters(): Int {
        return delegate.chapters.size
    }

    override fun getChapter(index: Int): Chapter {
        return ChapterImpl(this, index, delegate.chapters[index-1])
    }
}

class ChapterImpl(
    private val parentBook: Book,
    private val chapterIndex: Int,
    private val delegate: dnl.bible.json.v2.Chapter
) : Chapter {
    override fun getParent(): Book {
        return parentBook
    }

    override fun getIndex(): Int {
        return chapterIndex
    }

    override fun getAllVerses(): List<Verse> {
        return getIterator().asSequence().toList()
    }

    override fun getAllVersesStrings(): List<String> {
        return delegate.verses
    }

    override fun getNumOfVerses():Int {
        return delegate.verses.size
    }

    override fun getVerse(index: Int): Verse {
        return VerseImpl(delegate.verses[index-1], this, VerseLocation(parentBook.getBookEnum(), chapterIndex, index))
    }
}

class VerseImpl(
    private val verseText: String,
    private val parentChapter: Chapter,
    private val verseLocation: VerseLocation
) : Verse {
    override fun getParent(): Chapter {
        return parentChapter
    }

    override fun getIndex(): Int {
        return verseLocation.verseIndex
    }

    override fun getWords(): List<String> {
        return verseText.split(' ')
    }

    override fun getText(): String {
        return verseText
    }

}
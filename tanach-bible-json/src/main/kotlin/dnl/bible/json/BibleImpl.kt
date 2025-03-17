package dnl.bible.json

import dnl.bible.api.*
import dnl.bible.json.Locations.newVerseLocation

class BibleImpl(val delegate: SerializableBible) : Bible {
    override fun getBook(book: BibleBook): Book {
        return BookImpl(delegate.getBook(book))
    }
}

class BookImpl(private val delegate: SerializableBook) : Book {
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
        return ChapterImpl(this, index, delegate.chapters[index - 1])
    }

    override fun asVerseRange(): VerseRange {
        val firstVerse = Locations.newVerseLocation(getBookEnum(), 1, 1)
        val lastVerse = Locations.newVerseLocation(getBookEnum(), getNumOfChapters(), lastChapter().getNumOfVerses())
        return VerseRangeImpl(firstVerse, lastVerse)
    }
}

class ChapterImpl(
    private val parentBook: Book,
    private val chapterIndex: Int,
    private val delegate: SerializableChapter
) : Chapter {
    override fun getParent(): Book {
        return parentBook
    }

    override fun getIndex(): Int {
        return chapterIndex
    }

    override fun getAllVerses(): List<Verse> {
        return verseIterator().asSequence().toList()
    }

    override fun getAllVersesStrings(directive: TextDirective): List<String> {
        return delegate.verses
    }

    override fun getNumOfVerses(): Int {
        return delegate.verses.size
    }

    override fun getVerse(index: Int): Verse {
        require(index >= 1 && index <= delegate.verses.size, { "index $index not in boundaries" })
        val text = delegate.verses[index - 1]
        val textWithNiqqud = delegate.versesWithNiqqud[index - 1]
        return VerseImpl(
            text,
            textWithNiqqud,
            this,
            Locations.newVerseLocation(parentBook.getBookEnum(), chapterIndex, index)
        )
    }

    override fun asVerseRange(): VerseRange {
        val firstVerse = newVerseLocation(getParent().getBookEnum(), getIndex(), 1)
        val lastVerse = newVerseLocation(getParent().getBookEnum(), getIndex(), getNumOfVerses())
        return VerseRangeImpl(firstVerse, lastVerse)
    }
}

class VerseImpl(
    private val verseText: String,
    private val verseTextWithNiqqud: String,
    private val parentChapter: Chapter,
    private val verseLocation: VerseLocation
) : Verse {
    override fun getParent(): Chapter {
        return parentChapter
    }

    override fun getIndex(): Int {
        return verseLocation.verseIndex
    }

    override fun getWords(directive: TextDirective): List<String> {
        if (directive == TextDirective.DIACRITICS)
            verseTextWithNiqqud.split(' ')
        return verseText.split(' ')
    }

    override fun getText(directive: TextDirective): String {
        return verseText
    }

    override fun toString(): String {
        return "VerseImpl(verseText='$verseText', verseLocation=$verseLocation)"
    }

    override fun getLocation() = verseLocation
}
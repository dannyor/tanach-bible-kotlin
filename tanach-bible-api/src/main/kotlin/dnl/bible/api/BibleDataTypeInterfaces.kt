package dnl.bible.api

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
        return getBook(verseLocation.book).getChapter(verseLocation.chapterIndex).getVerse(verseLocation.verseIndex)
    }

    fun getVerseRange(verseRange: VerseRange): Iterator<Verse> {
        return VerseRangeIterator(getBook(verseRange.start.book), verseRange)
    }
}

/**
 * Note that bible indexes are 1 based.
 */
interface Book {
    fun getName(): String
    fun getHebrewName(): String
    fun getBookEnum(): BibleBook
    fun getNumOfChapters(): Int

    /**
     * Gets a chapter by a (1 based) index. That is, first chapter index is 1.
     */
    fun getChapter(index: Int): Chapter

    fun getVerse(location: VerseLocation): Verse {
        return getChapter(location.chapterIndex).getVerse(location.verseIndex)
    }

    fun getVerseRange(verseRange: VerseRange): Iterator<Verse> {
        return VerseRangeIterator(this, verseRange)
    }

    /**
     * Gets the whole book as VerseRange
     */
    fun asVerseRange(): VerseRange

    fun getIterator(): Iterator<Verse> {
        return VerseRangeIterator(this, asVerseRange())
    }

    fun lastChapter(): Chapter = getChapter(getNumOfChapters())
}

enum class TextDirective { SIMPLE, DIACRITICS }

interface Chapter {
    fun getParent(): Book
    fun getIndex(): Int
    fun getNumOfVerses(): Int
    fun getAllVerses(): List<Verse>
    fun getAllVersesStrings(directive: TextDirective = TextDirective.SIMPLE): List<String>

    /**
     *@param index 1 based index
     */
    fun getVerse(index: Int): Verse

    fun asVerseRange(): VerseRange
}

interface Verse {
    fun getParent(): Chapter
    fun getParentBook(): Book = getParent().getParent()
    fun getIndex(): Int
    fun getWords(directive: TextDirective = TextDirective.SIMPLE): List<String>
    fun getText(directive: TextDirective = TextDirective.SIMPLE): String
    fun getLocation(): VerseLocation
}





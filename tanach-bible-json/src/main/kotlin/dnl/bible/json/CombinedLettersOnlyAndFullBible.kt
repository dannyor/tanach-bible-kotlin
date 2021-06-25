package dnl.bible.json

import dnl.bible.api.Bible
import dnl.bible.api.BibleBook
import dnl.bible.api.Book
import dnl.bible.api.Chapter
import dnl.bible.api.Verse
import java.lang.UnsupportedOperationException

/**
 * Combines a letters only and full text (with nikud and teamim) bible.
 */
class CombinedLettersOnlyAndFullBible(val loBible: Bible, val full: Bible) : Bible {
    override fun getBook(book: BibleBook): Book = CombinedBook(loBible.getBook(book), full.getBook(book))
}

class CombinedBible(
    private val loBible: Bible,
    private val full: Bible
) {
    fun getBook(book: BibleBook): CombinedBook = CombinedBook(loBible.getBook(book), full.getBook(book))
}

class CombinedBook(
    private val loBook: Book,
    private val full: Book
) : Book {
    override fun getName(): String = loBook.getName()
    override fun getHebrewName(): String = loBook.getHebrewName()
    override fun getBookEnum(): BibleBook = loBook.getBookEnum()
    override fun getNumOfChapters(): Int = loBook.getNumOfChapters()
    override fun getChapter(index: Int): Chapter = getCombinedChapter(index)

    fun getCombinedChapter(index: Int): CombinedChapter =
        CombinedChapter(this, loBook.getChapter(index), full.getChapter(index))
}

class CombinedChapter(
    private val parentBook: CombinedBook,
    private val loChapter: Chapter,
    private val full: Chapter
) : Chapter {
    override fun getParent(): Book = parentBook
    override fun getIndex(): Int = loChapter.getIndex()
    override fun getNumOfVerses(): Int = loChapter.getNumOfVerses()

    fun getCombinedVerse(index: Int): CombinedVerse =
        CombinedVerse(this, loChapter.getVerse(index), full.getVerse(index))

    fun getAllCombinedVerses(): List<CombinedVerse> {
        val verses = mutableListOf<CombinedVerse>()
        for (i in 1..getNumOfVerses()) verses.add(getCombinedVerse(i))
        return verses
    }

    override fun getAllVerses(): List<Verse> = throw UnsupportedOperationException()
    override fun getAllVersesStrings(): List<String> = throw UnsupportedOperationException()
    override fun getVerse(index: Int): Verse = throw UnsupportedOperationException()
}

class CombinedVerse(
    private val parentChapter: CombinedChapter,
    private val loVerse: Verse,
    private val full: Verse
) : Verse {
    override fun getParent(): Chapter = parentChapter
    override fun getIndex(): Int = loVerse.getIndex()
    fun getCombinedWords(): List<Pair<String, String>> = loVerse.getWords().zip(full.getWords())
    fun getCombinedText(): Pair<String, String> = Pair(loVerse.getText(), full.getText())
    fun getLettersOnlyText(): String = loVerse.getText()
    fun getFullSymbolsText(): String = full.getText()
    override fun getWords(): List<String> = throw UnsupportedOperationException()
    override fun getText(): String = throw UnsupportedOperationException()
}
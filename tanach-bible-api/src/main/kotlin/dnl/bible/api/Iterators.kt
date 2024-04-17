package dnl.bible.api


interface LocationListener {
    fun onVerse(verse: Verse) {}
    fun onChapter(chapter: Chapter) {}
    fun onBook(book: Book) {}
}

fun Bible.bookIterator(): Iterator<Book> = BookIterator(this)
fun Bible.verseIterator(
    locationListener: LocationListener = object : LocationListener {}
): Iterator<Verse> =
    FullBibleVerseIterator(this, locationListener::onBook, locationListener::onChapter)

fun Book.chapterIterator(): Iterator<Chapter> = BookChapterIterator(this)
fun Chapter.verseIterator(): Iterator<Verse> = ChapterVerseIterator(this)
fun Book.verseIterator(onChapter: (chapter: Chapter) -> Unit = {}): Iterator<Verse> = BookVerseIterator(this, onChapter)

fun Iterator<Verse>.asList() = this.asSequence().toList()
fun Iterator<Verse>.toWordIterator(onVerse: (verse: Verse) -> Unit = {}): Iterator<String> = WordIterator(this, onVerse)

private class BookIterator(private val bible: Bible) : Iterator<Book> {
    private var bookIndex = 0
    private val books = BibleBook.values()

    override fun hasNext() = bookIndex < BibleBook.values().size
    override fun next() = bible.getBook(books[bookIndex++])
}

private class ChapterVerseIterator(private val chapter: Chapter) : Iterator<Verse> {
    private var verseIndex = 1
    override fun hasNext() = verseIndex <= chapter.getNumOfVerses()
    override fun next() = chapter.getVerse(verseIndex++)
}

private class BookChapterIterator(val book: Book) : Iterator<Chapter> {
    private var chapterIndex = 1
    override fun hasNext() = chapterIndex <= book.getNumOfChapters()
    override fun next() = book.getChapter(chapterIndex++)
}

class BookVerseIterator(book: Book, val onChapter: (chapter: Chapter) -> Unit = {}) : Iterator<Verse> {
    private val chapterIterator = book.chapterIterator()
    private var chapterVerseIterator = chapterIterator.next().verseIterator()
    override fun hasNext(): Boolean {
        if (chapterVerseIterator.hasNext()) return true
        if (!chapterIterator.hasNext()) return false
        return true
    }

    override fun next(): Verse {
        if (chapterVerseIterator.hasNext()) return chapterVerseIterator.next()
        chapterVerseIterator = chapterIterator.next().verseIterator()
        return chapterVerseIterator.next()
    }
}

class FullBibleVerseIterator(
    val bible: Bible,
    private val onBook: (book: Book) -> Unit = {},
    onChapter: (chapter: Chapter) -> Unit = {}
) : Iterator<Verse> {
    private val bookIterator = BookIterator(bible)
    private var bookVerseIterator = bookIterator.next().verseIterator(onChapter)

    override fun hasNext(): Boolean {
        if (bookVerseIterator.hasNext()) return true
        if (!bookIterator.hasNext()) return false
        return true
    }

    override fun next(): Verse {
        if (!bookVerseIterator.hasNext()) {
            if (!bookIterator.hasNext()) throw IllegalStateException("THE END. Did you call hasNext()?")
            val nextBook = bookIterator.next()
            onBook(nextBook)
            bookVerseIterator = nextBook.verseIterator()
        }
        return bookVerseIterator.next()
    }
}

class VerseRangeIterator(private val book: Book, private val range: VerseRange) : Iterator<Verse> {
    private lateinit var current: Verse

    override fun hasNext(): Boolean {
        if (!this::current.isInitialized) return true
        if (current.getParent().getIndex() == range.end.chapterIndex && current.getIndex() == range.end.verseIndex)
            return false
        return true
    }

    override fun next(): Verse {
        current = if (!this::current.isInitialized) {
            book.getVerse(range.start)
        } else {
            if (isAtChapterEnd()) {
                // first verse of next chapter
                current.getParentBook().getChapter(current.getParent().getIndex() + 1).getVerse(1)
            } else {
                current.getParent().getVerse(current.getIndex() + 1)
            }
        }
        return current
    }

    private fun isAtChapterEnd(): Boolean {
        return current.getLocation().verseIndex == current.getParent().getNumOfVerses()
    }
}

class WordIterator(
    val verseIterator: Iterator<Verse>,
    val onVerse: (verse: Verse) -> Unit = {}
) : Iterator<String> {
    private var currentVerse = verseIterator.next().getWords()
    private var indexInWord = 0

    override fun hasNext(): Boolean {
        if (indexInWord < currentVerse.size) return true
        if (!verseIterator.hasNext()) return false
        return verseIterator.hasNext()
    }

    override fun next(): String {
        if (indexInWord == currentVerse.size) {
            val nextVerse = verseIterator.next()
            onVerse(nextVerse)
            currentVerse = nextVerse.getWords()
            indexInWord = 0
        }
        return currentVerse[indexInWord++]
    }
}

class CharacterIterator(
    private val verseIterator: Iterator<Verse>,
    val onVerse: (verse: Verse) -> Unit = {},
    val onWord: (word: String) -> Unit = {}
) : Iterator<Char> {
    private lateinit var currentVerse: Verse
    private lateinit var currentVerseWords: List<String>
    private var wordIndex = 0
    private var charIndex = 0
    private var theEnd = false

    init {
        nextVerse()
    }

    override fun hasNext(): Boolean {
        return !theEnd
    }

    private fun nextVerse() {
        currentVerse = verseIterator.next()
        currentVerseWords = currentVerse.getWords()
        wordIndex = 0
        charIndex = 0
    }

    private fun currentWord() = currentVerseWords[wordIndex]
    override fun next(): Char {
        val isLastCharInWord = charIndex == currentWord().length - 1
        val isLastWord = wordIndex == currentVerseWords.size-1
        val isLastCharInVerse = isLastWord && isLastCharInWord
        if (wordIndex == 0 && charIndex == 0) { // start of verse
            onVerse(currentVerse)
        }

        val retVal = currentWord()[charIndex]

        if (isLastCharInVerse) {
            if (verseIterator.hasNext()) {
                nextVerse()
            } else {
                theEnd = true
            }

        } else {
            if (charIndex < currentWord().length - 1)
                charIndex++
            else {
                wordIndex++
                charIndex = 0
                if (verseIterator.hasNext())
                    onWord(currentWord())
            }
        }
        return retVal
    }
}
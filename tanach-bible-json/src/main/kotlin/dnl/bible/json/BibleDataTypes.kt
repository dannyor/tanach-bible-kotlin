package dnl.bible.json

import dnl.bible.api.*
import dnl.bible.api.Verse
import kotlinx.serialization.Serializable
import org.apache.commons.lang3.StringUtils

@Serializable
data class Bible(val books: List<Book>) : dnl.bible.api.Bible {
    override fun getBook(book: BibleBook): dnl.bible.api.Book {
        books.forEach { if (it.getName() == book.englishName) return it }
        throw IllegalArgumentException("No such book: ${book.englishName}")
    }
}

@Serializable
data class Book(private val name: String, val numOfChapters: Int, val numOfVerses: Int) : dnl.bible.api.Book {
    val chapters = mutableListOf<Chapter>()

    override fun getName(): String {
        return name
    }

    override fun getChapter(index: Int): dnl.bible.api.Chapter {
        val chapter = chapters[index - 1]
        chapter.parentBook = this // this is a hack because these classes are attached to the file format
        return chapter
    }
}

@Serializable
data class Chapter(val chapterIndex: Int, val numOfVerses: Int) : dnl.bible.api.Chapter {
    val verses = mutableListOf<String>()

    @kotlinx.serialization.Transient
    lateinit var parentBook: Book

    override fun getParent(): dnl.bible.api.Book {
        return parentBook
    }

    override fun getIndex(): Int {
        return chapterIndex
    }

    override fun getVerse(index: Int): Verse {
        return dnl.bible.json.Verse(this, index, getVerseAsString(index))
    }

    override fun getVerseAsString(index: Int): String {
        return verses[index - 1]
    }

    override fun hasNext(): Boolean {
        return chapterIndex + 1 <= parentBook.chapters.size
    }

    override fun next(): dnl.bible.api.Chapter {
        return parentBook.getChapter(chapterIndex + 1)
    }
}

data class Verse(val parent: Chapter, private val index: Int, private val text: String) : dnl.bible.api.Verse {
    override fun getParent(): dnl.bible.api.Chapter {
        return parent
    }

    override fun getIndex(): Int {
        return index
    }

    override fun getWords(): List<String> {
        return StringUtils.split(text, ' ').toList()
    }

    override fun getText(): String {
        return text
    }

    override fun hasNext(): Boolean {
        return index + 1 <= parent.numOfVerses || parent.hasNext()
    }

    override fun next(): Verse {
        if (index + 1 <= parent.numOfVerses)
            return parent.getVerse(index + 1)
        return parent.next().getVerse(1)
    }
}
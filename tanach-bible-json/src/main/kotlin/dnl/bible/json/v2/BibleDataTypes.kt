package dnl.bible.json.v2

import dnl.bible.api.BibleBook
import kotlinx.serialization.Serializable

@Serializable
data class Bible(val books: List<Book>) : dnl.bible.api.v2.Bible {
    override fun getBook(book: BibleBook): dnl.bible.api.v2.Book {
        books.forEach { if (it.name == book.englishName) return it }
        throw IllegalArgumentException("No such book: ${book.englishName}")
    }
}

@Serializable
data class Book(
    override val name: String,
    override val hebrewName: String,
    val numOfVerses: Int,
    override val bookEnumVal: BibleBook,
    val chapters: List<Chapter>
) : dnl.bible.api.v2.Book {

//    override fun getNumOfChapters(): Int {
//        return chapters.size
//    }

    override fun getChapter(index: Int): dnl.bible.api.v2.Chapter {
        val chapter = chapters[index - 1]
        chapter.parentBook = this // this is a hack because these classes are attached to the file format
        return chapter
    }
}

@Serializable
data class Chapter(
    override val verses: List<String>
) : dnl.bible.api.v2.Chapter {
    @kotlinx.serialization.Transient
    lateinit var parentBook: Book

    override fun getParent(): dnl.bible.api.v2.Book {
        return parentBook
    }
}
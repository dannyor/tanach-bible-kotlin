package dnl.bible.json.v2

import dnl.bible.api.BibleBook
import kotlinx.serialization.Serializable

@Serializable
data class Bible(val books: List<Book>) {
    fun getBook(book: BibleBook): Book {
        books.forEach { if (it.bookEnumVal.englishName == book.englishName) return it }
        throw IllegalArgumentException("No such book: ${book.englishName}")
    }
}

@Serializable
data class Book(
    val name: String,
    val hebrewName: String,
    val numOfVerses: Int,
    val bookEnumVal: BibleBook,
    val chapters: List<Chapter>
)

@Serializable
data class Chapter(
    val verses: List<String>
) {
    @kotlinx.serialization.Transient
    lateinit var parentBook: Book

    fun getParent(): Book {
        return parentBook
    }
}
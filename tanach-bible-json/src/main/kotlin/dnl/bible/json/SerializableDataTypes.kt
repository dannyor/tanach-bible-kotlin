package dnl.bible.json

import dnl.bible.api.BibleBook
import kotlinx.serialization.Serializable

@Serializable
data class SerializableBible(val books: List<SerializableBook>) {
    fun getBook(book: BibleBook): SerializableBook {
        books.forEach { if (it.bookEnumVal.englishName == book.englishName) return it }
        throw IllegalArgumentException("No such book: ${book.englishName}")
    }
}

@Serializable
data class SerializableBook(
    val name: String,
    val hebrewName: String,
    val numOfVerses: Int,
    val bookEnumVal: BibleBook,
    val chapters: List<SerializableChapter>
)

@Serializable
data class SerializableChapter(
    val verses: List<String>,
    val versesWithNiqqud: List<String>
) {
    @kotlinx.serialization.Transient
    lateinit var parentBook: SerializableBook

    fun getParent(): SerializableBook {
        return parentBook
    }
}
@Serializable
data class SerializableWord(
//    val fullText: String,
    val text: String,
    val withNiqqud: String
)
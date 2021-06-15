package dnl.uxlc.xml.xstream

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit
import com.thoughtworks.xstream.annotations.XStreamOmitField
import dnl.bible.api.BibleBook
import dnl.uxlc.xml.XmlConversion
import java.io.Reader

class XStreamConversion : XmlConversion {
    override fun convert(reader: Reader): dnl.bible.api.v2.Book {
        val xStream = XStream()
        xStream.ignoreUnknownElements()
        xStream.processAnnotations(Root::class.java)
        val root = xStream.fromXML(reader) as Root
        return convert(root.tanach.books[0])
    }

    fun convert(book: Book): dnl.bible.api.v2.Book {
        val chapters = mutableListOf<dnl.bible.json.v2.Chapter>()

        var numOfVerses = 0

        book.chapters.forEachIndexed { index, chapter ->
            val convertedChapter = convert(chapter, index)
            chapters.add(convertedChapter)
            numOfVerses += chapter.numOfVerses
        }
        val result = dnl.bible.json.v2.Book(
            book.names.name,
            book.names.hebrewname,
            numOfVerses,
            BibleBook.byEnglishName(book.names.name),
            chapters
        )
        chapters.forEach { it.parentBook = result }
        return result
    }

    fun convert(chapter: Chapter, index: Int): dnl.bible.json.v2.Chapter {
        val verses = mutableListOf<dnl.bible.json.v2.Verse>()
        chapter.verses.forEachIndexed { index, verse ->
            val convertedVerse = dnl.bible.json.v2.Verse(index + 1, verse.words)
            verses.add(convertedVerse)
        }
        val result = dnl.bible.json.v2.Chapter(verses, index, chapter.numOfVerses)
        verses.forEach { it.parent = result }
        return result
    }
}


data class Verse(
    @XStreamAlias("w") @XStreamImplicit val words: List<String>,
    @XStreamOmitField val pe: Any
)

@XStreamAlias("c")
data class Chapter(
    @XStreamAlias("v") @XStreamImplicit val verses: List<Verse>,
    @XStreamAlias("vs") val numOfVerses: Int
)

@XStreamAlias("name")
data class Names(
    val name: String,
    val abbrev: String,
    val number: String,
    val filename: String,
    val hebrewname: String
)


data class Book(
    val names: Names,
    @XStreamAlias("c") @XStreamImplicit val chapters: List<Chapter>
)


data class Tanach(
    @XStreamAlias("book") @XStreamImplicit val books: List<Book>
)

@XStreamAlias("Tanach")
data class Root(
    @XStreamAlias("tanach") val tanach: Tanach
)
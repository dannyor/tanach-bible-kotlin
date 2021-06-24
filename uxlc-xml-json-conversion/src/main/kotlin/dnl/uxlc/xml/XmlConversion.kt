package dnl.uxlc.xml

import dnl.bible.api.BibleBook
import dnl.bible.api.v2.Book
import java.io.Reader

interface XmlConversion {
    fun convert(reader: Reader, bibleBook: BibleBook): Book
}
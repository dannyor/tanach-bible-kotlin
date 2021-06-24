package dnl.uxlc.xml

import dnl.bible.api.BibleBook
import java.io.Reader

interface XmlConversion {
    fun convert(reader: Reader, bibleBook: BibleBook): dnl.bible.json.v2.Book
}
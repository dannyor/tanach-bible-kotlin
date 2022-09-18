package dnl.uxlc.xml

import dnl.bible.api.BibleBook
import dnl.bible.json.SerializableBook
import java.io.Reader

interface XmlConversion {
    fun convert(reader: Reader, bibleBook: BibleBook): SerializableBook
}
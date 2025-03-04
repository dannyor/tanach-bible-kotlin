package dnl.bible.json

import dnl.bible.api.BibleBook
import dnl.bible.api.VerseLocation
import dnl.bible.api.HebrewNumberingSystem.parseHebrewString
import dnl.bible.api.Locations.newVerseLocation

object ToStringUtil {
    fun parseHebString( hebStr:String): VerseLocation {
        val s = hebStr.replace("[", "").replace("]", "")
        val splits = s.split(':')
        val book = BibleBook.byHebrewName(splits[0].trim())
        val chapter = Int.parseHebrewString(splits[1].trim())
        val verse = Int.parseHebrewString(splits[2].trim())
        return newVerseLocation(book, chapter, verse)
    }
}
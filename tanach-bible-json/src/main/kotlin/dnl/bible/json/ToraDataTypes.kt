package dnl.bible.json

//import dnl.bible.api.*
//import dnl.bible.api.Verse
//import kotlinx.serialization.Serializable
//import org.apache.commons.lang3.StringUtils
//
//
//class ToraImpl(private val bible: Bible) : Tora {
//    override fun getHumash(humash: HumashEnum): dnl.bible.api.Book {
//        return bible.getBook(humash.bibleBook)
//    }
//
//    override fun getParasha(parasha: ParashaEnum): Parasha {
//        val book = bible.getBook(parasha.range.start.book)
//        val verses = book.getVerseRange(parasha.range).asSequence().toList()
//        return ParashaImpl(parasha, verses)
//    }
//}
//
//data class ParashaImpl(val parashaEnum: ParashaEnum, private val verses: List<Verse>) : Parasha {
//    override fun getName(): String {
//        return parashaEnum.englishName
//    }
//
//    override fun getVerses(): List<Verse> {
//        return verses
//    }
//}

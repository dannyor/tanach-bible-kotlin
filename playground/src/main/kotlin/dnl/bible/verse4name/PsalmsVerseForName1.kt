package dnl.bible.verse4name

import dnl.bible.api.Bible
import dnl.bible.api.BibleBook
import dnl.bible.api.HebrewChars
import dnl.bible.api.Verse
import dnl.bible.json.BibleLoader
import java.io.File

class PsalmsVerseForName1(val bible: Bible, val personsName:String) {

    fun getMatchingVerses() : List<Verse> {
        val result = mutableListOf<Verse>()
        bible.getBook(BibleBook.PSALMS).getIterator().forEach { verse ->
            if(verse.getText().startsWith(personsName[0]) && verse.getText().endsWith(personsName[0])) {
                result.add(verse)
            }
        }
        return result
    }
}

fun main() {
    val bible = BibleLoader.loadJustLettersBible(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip")
    )
    for(c in HebrewChars.ALEF.char..HebrewChars.TAV.char) {
        println("_________________________________________________$c")
        PsalmsVerseForName1(bible, c.toString()).getMatchingVerses().forEach {
            println("${it.getText()} ${it.getLocation().toStringHeb()}")
        }
    }
}
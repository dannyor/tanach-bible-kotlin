package dnl.bible.verse4name

import com.google.common.collect.ArrayListMultimap
import dnl.bible.api.*
import dnl.bible.api.BibleBook.PSALMS
import dnl.bible.json.BibleLoader
import java.io.File

class PsalmsVerseForName1(
    val bible: Bible, private val personName: String
) {

    private val books: List<BibleBook> = BibleGroups.TORA.books.asList() + PSALMS

    fun getMatchingVerses(): List<Verse> {
        val result = mutableListOf<Verse>()
        books.forEach {
            bible.getBook(it).getIterator().forEach { verse ->
                if (
                    (verse.getText().startsWith(personName.first()) && verse.getText().endsWith(personName.last())) ||
                    verse.getText().contains(personName)
                ) {
                    result.add(verse)
                }
            }
        }
        return result
    }
}

fun main() {
    val bible = BibleLoader.loadJustLettersBible(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip")
    )

    val results = ArrayListMultimap.create<String, Verse>()
    PsalmsVerseForName1(bible, "איילת").getMatchingVerses().forEach {
        results.put(it.getParentBook().getHebrewName(), it)
    }
    val keys = results.keys()
    println(keys)
    results.keySet().forEach { bookName ->
        println("--- $bookName ---------------------------")
        println(results.get(bookName).size)
        results.get(bookName).forEach { verse ->
            println("${verse.getText()} ${verse.getLocation().toStringHeb()}")
        }
    }

}
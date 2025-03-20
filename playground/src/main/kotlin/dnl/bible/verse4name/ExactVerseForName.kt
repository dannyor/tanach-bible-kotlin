package dnl.bible.verse4name

import dnl.bible.api.*
import dnl.bible.json.bible

class ExactVerseForName(
    val bible: Bible, private val name: String
) {

    val iterator = bible.verseIterator()
    val result = mutableListOf<MatchedVerse>()


    fun process() {
        iterator.forEach {
            val text = it.getText()
            if (text.startsWith(name[0]) && text.endsWith(name.last())) {
                verseMatches(it)
            }
        }
    }

    private fun verseMatches(verse: Verse) {
        var charIndex = 1
        val indexes = mutableListOf<Int>()
        var index = 0
        CharacterIterator(listOf(verse).iterator()).forEach {
            if (it == name[charIndex]) {
                indexes.add(index)
                if (charIndex == name.length - 2) {
                    println("${verse.getLocation().toStringHeb()}-${verse.getText()}")
                    result.add(MatchedVerse(verse.getLocation(), indexes))
                    return
                }
                charIndex++
            }
            index++
        }
    }
}

data class MatchedVerse(
    val verseLocation: VerseLocation,
    val charIndexes: List<Int>
)

fun main() {
    val exactVerseForName = ExactVerseForName(bible, "נוה")
    exactVerseForName.process()
    exactVerseForName.result.sortWith { v1, v2 ->
        v1.verseLocation.book.ordinal - v1.verseLocation.book.ordinal
    }
    exactVerseForName.result.forEach { matchedVerse ->
        val verse = bible.getVerse(matchedVerse.verseLocation)
        println("${verse.getText()} ${matchedVerse.verseLocation.toStringHeb()}")
    }
}
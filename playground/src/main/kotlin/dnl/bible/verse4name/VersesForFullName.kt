package dnl.bible.verse4name

import dnl.bible.api.Bible
import dnl.bible.api.Verse
import dnl.bible.api.toWordIterator
import dnl.bible.api.verseIterator
import dnl.bible.json.BibleLoader
import java.io.File

class VersesForFullName(bible: Bible, val word: String) {
    private val wordIterator = bible.verseIterator().toWordIterator(this::onVerse)
    private lateinit var currentVerse:Verse

    fun run(): List<String> {
        val result = mutableListOf<String>()
        var matchIndex = 0
        var tmpMatchingWords = mutableListOf<String>()

        while (wordIterator.hasNext()) {
            val w = wordIterator.next()
            if(w.startsWith(word[matchIndex++])){
                tmpMatchingWords.add(w)
                if(matchIndex == word.length) {
                    result.add(tmpMatchingWords.joinToString(" ")+" "+currentVerse.getLocation().toStringHeb())
                    tmpMatchingWords.clear()
                    matchIndex = 0
                }
            } else {
                tmpMatchingWords.clear()
                matchIndex = 0
            }
        }
        return result
    }

    private fun onVerse(verse: Verse){
        currentVerse = verse
    }
}

fun main() {
    val bible = BibleLoader.loadJustLettersBible(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip")
    )
    val v = VersesForFullName(bible, "דס")
    val result = v.run()
    result.forEach { println(it) }
}
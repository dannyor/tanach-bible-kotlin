package dnl.bible.verse4name

import dnl.bible.api.*
import dnl.bible.json.BibleLoader
import java.io.File
import java.lang.StringBuilder

class VersesForFullName(
    bible: Bible,
    val word: String,
    findCombinations: Boolean = false
) {
    private val wordIterator = bible.verseIterator().toWordIterator(this::onVerse)
    private lateinit var currentVerse: Verse
    private val permutations = if (findCombinations) word.permute().toSet() else listOf(word)
    private val buckets: List<ResultBucket> = permutations.map { ResultBucket(it) }
    fun run(): List<Result> {
        val results = mutableListOf<Result>()
        var matchIndex = 0
        var tmpMatchingWords = mutableListOf<String>()

        while (wordIterator.hasNext()) {
            val w = wordIterator.next()
            buckets.forEach { if (it.add(w)) results.add(it.result) }
        }
        return results
    }

    private fun onVerse(verse: Verse) {
        currentVerse = verse
    }

    private inner class ResultBucket(val aWord: String) {
        var matchIndex = 0
        var tmpMatchingWords = mutableListOf<String>()
        lateinit var result: Result

        fun add(w: String): Boolean {
            if (w.startsWith(aWord[matchIndex++])) {
                tmpMatchingWords.add(w)
                if (matchIndex == aWord.length) {
//                    result = tmpMatchingWords.joinToString(" ") + " " + currentVerse.getLocation().toStringHeb()
                    result = Result(aWord, tmpMatchingWords.toList(), currentVerse.getLocation())
                    tmpMatchingWords.clear()
                    matchIndex = 0
                    return true
                }
            } else {
                tmpMatchingWords.clear()
                matchIndex = 0
            }
            return false
        }
    }

    data class Result(val word: String, val matchingCombination: List<String>, val verseLocation: VerseLocation) {
        fun describe() = "$word: ${matchingCombination.joinToString(" ")} ${verseLocation.toStringHeb()}"
    }
}

fun main() {
    val bible = BibleLoader.loadJustLettersBible(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip")
    )

    val findCombinations = true
    val v = VersesForFullName(bible, "אסתר", findCombinations)
    val result = v.run()
    val sb = StringBuilder()

    if(findCombinations) {
        sb.append("\"צירופי השם ")
    } else {
        sb.append("השם ")
        sb.append("\"")
    }
    sb.append(v.word)
    sb.append("\" ")
    sb.append("כראשי תיבות במקרא:")
    sb.append("\n")
    sb.append("".padEnd(sb.length, '-'))
    println(sb)
    result.forEach { println(it.describe()) }
}
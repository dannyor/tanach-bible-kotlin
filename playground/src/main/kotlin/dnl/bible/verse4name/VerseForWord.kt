package dnl.bible.verse4name

import dnl.bible.api.*
import dnl.bible.json.bible
import java.io.File

enum class SearchType { ACRONYM, ENDS }

class VerseForWord(
    bible: Bible,
    val word: String,
    findCombinations: Boolean = false,
    searchType: SearchType
) {
    private val wordIterator = bible.verseIterator().toWordIterator(this::onVerse)
    private lateinit var currentVerse: Verse
    private val permutations:Collection<String>
    private val buckets: List<ResultBucket>

    init {
        val actualWord = when(searchType){
            SearchType.ACRONYM -> HebrewStringUtils.toNonFinalChars(word)
            SearchType.ENDS -> HebrewStringUtils.toFinalChars(word)
        }
        permutations = if (findCombinations) actualWord.permute().toSet() else listOf(actualWord)
        buckets = permutations.map {
            val matcher = if (searchType == SearchType.ACRONYM) AcronymMatcher(it) else WordEndsMatcher(it)
            ResultBucket(matcher)
        }
    }

    fun run(): List<Result> {
        val results = mutableListOf<Result>()

        while (wordIterator.hasNext()) {
            val w = wordIterator.next()
            buckets.forEach { if (it.add(w)) results.add(it.result) }
        }
        return results
    }

    private fun onVerse(verse: Verse) {
        currentVerse = verse
    }

    private inner class ResultBucket(val wordIndexMatcher: WordIndexMatcher) {
        lateinit var result: Result

        fun add(w: String): Boolean {
            if (wordIndexMatcher.matchNext(w)) {
                if (wordIndexMatcher.matchCompleted()) {
                    result = Result(wordIndexMatcher.word, (wordIndexMatcher.matchingWords.toList()), currentVerse.getLocation())
                    wordIndexMatcher.reset()
                    return true
                }
            } else {
                wordIndexMatcher.reset()
            }
            return false
        }
    }

}


interface WordIndexMatcher {
    val word: String
    val matchingWords: List<String>
    fun matchNext(word: String): Boolean
    fun matchCompleted(): Boolean
    fun reset()
}

open class BaseMatcher(
    override val word: String,
    val matchCondition: (baseWord: String, wordToMatch: String, index: Int) -> Boolean
) :
    WordIndexMatcher {
    override val matchingWords = mutableListOf<String>()
    private var index = 0

    override fun matchNext(matchWord: String): Boolean {
        if(index == word.length) return false

        if (matchCondition(word, matchWord, index)) {
            matchingWords.add(matchWord)
            index++
            return true
        }
        if (index > 0) reset()
        return false
    }

    override fun matchCompleted() = index == word.length

    override fun reset() {
        matchingWords.clear()
        index = 0
    }

}

class AcronymMatcher(word: String) :
    BaseMatcher(word, { w1, w2, i -> w2.startsWith(w1[i]) })

class WordEndsMatcher(word: String) :
    BaseMatcher(word, { w1, w2, i -> w2.endsWith(w1[i]) })

data class Result(val word: String, val matchingCombination: List<String>, val verseLocation: VerseLocation)


fun main() {
    val findCombinations = false
    val v = VerseForWord(bible, "עוקשי", findCombinations, SearchType.ACRONYM)
    val result = v.run()
    val sb = StringBuilder()

    if (findCombinations) {
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

    result.forEach {
        it.apply {
            val message = "$word: ${matchingCombination.joinToString(" ")} ${verseLocation.toStringHeb()}"
            println(message)
        }
    }
}
package dnl.tnc.queries

import dnl.bible.api.*
import org.slf4j.LoggerFactory


interface TraversalWordVisitor {
    fun visitWord(word: String, wordWithDiacritics: String, verseLocation: VerseLocation, wordIndex: Int)
}

class VersesWordTraversal(
    private val verseIterator: Iterator<Verse>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun traverse(visitor: TraversalWordVisitor) {
        BibleBook.entries.forEach { bibleBook ->
            logger.info("process()ing $bibleBook")
            verseIterator.forEach { verse ->
                val words = verse.getWords(TextDirective.SIMPLE)
                val wordsWithDiacritics = verse.getWords(TextDirective.DIACRITICS)
                for (i in words.indices) {
                    visitor.visitWord(words[i], wordsWithDiacritics[i], verse.getLocation(), i)
                }
            }
        }
    }
}

class GenericWordVisitorWithResults(val condition: (word: String) -> Boolean) : TraversalWordVisitor {
    val results = mutableListOf<WordResult>()

    override fun visitWord(word: String, wordWithDiacritics: String, verseLocation: VerseLocation, wordIndex: Int) {
        if (condition(word)) {
            results.add(
                WordResult(
                    word,
                    wordWithDiacritics,
                    verseLocation.wordLocation(wordIndex)
                )
            )
        }
    }

    fun getGroupedResults(): GroupedWordResults {
        val groupedByWord: MutableMap<String, MutableList<WordResult>> =
            results.groupByTo(mutableMapOf()) { it.wordWithDiacritics }
        val groupedResults = mutableListOf<WordResults>()
        groupedByWord.values.forEach { listOfWordResult ->
            val locations: List<WordLocation> = listOfWordResult.map { it.wordLocation }
            groupedResults.add(
                WordResults(
                    listOfWordResult[0].word,
                    listOfWordResult[0].wordWithDiacritics,
                    locations
                )
            )
        }
        val totalFound = groupedResults.sumOf { it.wordLocations.size }
        return GroupedWordResults(totalFound, groupedResults)
    }
}
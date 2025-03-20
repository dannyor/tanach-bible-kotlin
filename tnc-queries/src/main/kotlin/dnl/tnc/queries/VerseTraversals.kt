package dnl.tnc.queries

import dnl.bible.api.*
import org.slf4j.LoggerFactory


interface TraversalVerseVisitor {
    fun visitVerse(verse: Verse)
}

class BibleVerseTraversal(
    private val bible: Bible
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun traverse(visitor: TraversalVerseVisitor) {
        BibleBook.entries.forEach { bibleBook ->
            logger.info("process()ing $bibleBook")
            bible.getBook(bibleBook).getIterator().forEach { verse ->
                visitor.visitVerse(verse)
            }
        }
    }
}

class GenericVerseVisitorWithResults(val condition: (verse: Verse) -> Boolean) : TraversalVerseVisitor {
    val results = mutableListOf<VerseResult>()

    override fun visitVerse(verse: Verse) {
        if (condition(verse)) {
            results.add(
                VerseResult(
                    verse.getLocation(),
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
package dnl.tnc.queries

import dnl.bible.api.BibleBook
import dnl.bible.api.Verse
import dnl.bible.api.VerseInnerLocations
import org.slf4j.LoggerFactory


interface TraversalVerseVisitor {
    fun visitVerse(verse: Verse)
}

class VerseTraversal(
    private val verseIterator: Iterator<Verse>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun traverse(visitor: TraversalVerseVisitor) {
        BibleBook.entries.forEach { bibleBook ->
            logger.info("process()ing $bibleBook")
            verseIterator.forEach { verse ->
                visitor.visitVerse(verse)
            }
        }
    }
}

class GenericVerseVisitorWithResults(val condition: (verse: Verse) -> Boolean) : TraversalVerseVisitor {
    val results = mutableListOf<VerseInnerLocations>()

    override fun visitVerse(verse: Verse) {
        if (condition(verse)) {
            results.add(
                VerseInnerLocations(
                    verse.getLocation(),
                    listOf(),
                    listOf()
                )
            )
        }
    }

    fun getGroupedResults(): GroupedByBookVerseResults {
        val groupedByBook: MutableMap<BibleBook, MutableList<VerseInnerLocations>> =
            results.groupByTo(mutableMapOf()) { it.verseLocation.book }
        val groupedResults = mutableListOf<VerseResultsInBook>()
        groupedByBook.entries.forEach { entry ->
            groupedResults.add(
                VerseResultsInBook(
                    entry.key,
                    entry.value
                )
            )
        }
        val totalFound = results.size
        return GroupedByBookVerseResults(totalFound, groupedResults)
    }
}
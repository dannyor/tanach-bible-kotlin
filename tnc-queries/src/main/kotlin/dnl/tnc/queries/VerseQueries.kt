package dnl.tnc.queries

import dnl.bible.api.HebrewStringUtils
import dnl.bible.api.Verse

class VerseQueries {

    fun verseForNameStartAndEnd(name: String, verseIterator: Iterator<Verse>): GroupedByBookVerseResults {
        val nameWithNonFinals = HebrewStringUtils.toNonFinalChars(name)

        val visitor = GenericVerseVisitorWithResults { verse: Verse ->
            val text = verse.getText()
            text.first() == nameWithNonFinals.first() &&
                    (text.last() == name.last() || text.last() == nameWithNonFinals.last())
        }

        VerseTraversal(verseIterator).traverse(visitor)
        return visitor.getGroupedResults()
    }
}
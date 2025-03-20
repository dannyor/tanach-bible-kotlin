package dnl.tnc.queries

import dnl.bible.api.Gematria
import dnl.bible.api.Verse

class SingleWordQueries {

    fun queryForSameGimatria(verseIterator: Iterator<Verse>, rootWord: String): GroupedWordResults {
        val gematriaOfRootWord = Gematria.gematriaOf(rootWord)
        val visitor = GenericWordVisitorWithResults { word: String -> Gematria.gematriaOf(word) == gematriaOfRootWord }
        VersesWordTraversal(verseIterator).traverse(visitor)
        return visitor.getGroupedResults()
    }

}
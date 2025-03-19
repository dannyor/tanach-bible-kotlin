package dnl.tnc.queries

import dnl.bible.api.Bible
import dnl.bible.api.Gematria

class SingleWordQueries {

    fun queryForSameGimatria(bible: Bible, rootWord: String): GroupedWordResults {
        val gematriaOfRootWord = Gematria.gematriaOf(rootWord)
        val visitor = GenericVisitorWithResults { word: String -> Gematria.gematriaOf(word) == gematriaOfRootWord }
        BibleWordTraversal(bible).traverse(visitor)
        return visitor.getGroupedResults()
    }

}
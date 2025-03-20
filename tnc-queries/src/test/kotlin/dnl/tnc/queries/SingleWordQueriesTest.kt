package dnl.tnc.queries

import dnl.bible.api.verseIterator
import dnl.tnc.bible
import kotlin.test.Test

class SingleWordQueriesTest {

    @Test
    fun testWordGimatria(){
        val groupedWordResults = SingleWordQueries().queryForSameGimatria(bible.verseIterator(), "שלום")
        //println(json.encodeToString(groupedWordResults))
        println(ToString.toString(groupedWordResults))
    }
}
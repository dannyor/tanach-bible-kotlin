package dnl.tnc.queries

import dnl.tnc.bible
import dnl.tnc.json
import kotlin.test.Test

class SingleWordQueriesTest {

    @Test
    fun testWordGimatria(){
        val groupedWordResults = SingleWordQueries().queryForSameGimatria(bible, "שלום")
        //println(json.encodeToString(groupedWordResults))
        println(ToString.toString(groupedWordResults))
    }
}
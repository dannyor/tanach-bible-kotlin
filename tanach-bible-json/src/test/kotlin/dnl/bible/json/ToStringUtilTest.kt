package dnl.bible.json

import dnl.bible.api.BibleBook
import dnl.bible.api.toStringHeb
import dnl.bible.json.Locations.newVerseLocation
import org.junit.jupiter.api.Test

internal class ToStringUtilTest {

    @Test
    fun testVerseLocationToString() {
        val toStringHeb = newVerseLocation(BibleBook.ESTHER, 1, 1).toStringHeb()
        println(toStringHeb)
        val vl = ToStringUtil.parseHebString(toStringHeb)
        println(vl)
    }

}
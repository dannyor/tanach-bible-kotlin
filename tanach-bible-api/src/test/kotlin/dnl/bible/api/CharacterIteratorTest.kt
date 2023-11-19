package dnl.bible.api

import kotlin.test.Test
import kotlin.test.assertEquals

class CharacterIteratorTest {

    @Test
    fun test() {
        val verse:Verse = TestVerse("one two three")
        val iterator = CharacterIterator(listOf(verse).iterator())
        val sb = StringBuilder()
        iterator.forEach {
            sb.append(it)
        }
        assertEquals("onetwothree", sb.toString())
    }

}
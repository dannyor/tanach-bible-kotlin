package dnl.bible.api

import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("SpellCheckingInspection")
class CharacterIteratorTest {

    @Test
    fun testSingleVerse() {
        val verse:Verse = TestVerse("one two three")
        val iterator = CharacterIterator(listOf(verse).iterator())
        val sb = StringBuilder()
        iterator.forEach {
            sb.append(it)
        }
        assertEquals("onetwothree", sb.toString())
    }

    @Test
    fun testMultipleVerses() {
        val verses = listOf(
            TestVerse("one two three"),
            TestVerse("four five six"),
            TestVerse("seven eight nine")
        )
        val iterator = CharacterIterator(verses.iterator())
        val sb = StringBuilder()
        iterator.forEach {
            sb.append(it)
        }
        assertEquals("onetwothreefourfivesixseveneightnine", sb.toString())
    }

}
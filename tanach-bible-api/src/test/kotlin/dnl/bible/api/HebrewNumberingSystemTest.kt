package dnl.bible.api

import dnl.bible.api.HebrewNumberingSystem.Companion.toHebrewString
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class HebrewNumberingSystemTest {

    @Test
    fun toHebrewString() {
        assertEquals("יג", 13.toHebrewString())
        assertEquals("כז", 27.toHebrewString())
        assertEquals("נה", 55.toHebrewString())
        assertEquals("פו", 86.toHebrewString())
        assertEquals("צט", 99.toHebrewString())
        assertEquals("קא", 101.toHebrewString())
        assertEquals("קפא", 181.toHebrewString())
        assertEquals("שנה", 355.toHebrewString())
        assertEquals("תפ", 480.toHebrewString())
        assertEquals("תשסג", 763.toHebrewString())
        assertEquals("תתכא", 821.toHebrewString())
        assertEquals("תתקצט", 999.toHebrewString())
        assertEquals("א׳של", 1330.toHebrewString())
        assertEquals("ה׳תתכד", 5824.toHebrewString())
    }
}
package dnl.bible.api

import java.lang.IllegalArgumentException

class HebrewNumberingSystem {
    companion object {

        val m1to19 = mapOf(
            0 to "",
            1 to "א",
            2 to "ב",
            3 to "ג",
            4 to "ד",
            5 to "ה",
            6 to "ו",
            7 to "ז",
            8 to "ח",
            9 to "ט",
            10 to "י",
            11 to "יא",
            12 to "יב",
            13 to "יג",
            14 to "יד",
            15 to "טו",
            16 to "טז",
            17 to "יז",
            18 to "חי",
            19 to "יט"
        )

        val m10s = mapOf(
            0 to "",
            1 to "י",
            2 to "כ",
            3 to "ל",
            4 to "מ",
            5 to "נ",
            6 to "ס",
            7 to "ע",
            8 to "פ",
            9 to "צ"
        )

        val m100s = mapOf(
            1 to "ק",
            2 to "ר",
            3 to "ש",
            4 to "ת",
            5 to "קת",
            6 to "תר",
            7 to "תש",
            8 to "תת",
            9 to "תתק",
        )

        fun Int.toHebrewString(): String {
            if (this >= 10000) throw IllegalArgumentException("")
            if (this < 20) return m1to19[this]!!
            val tensDigit = (this / 10) % 10
            val tensAndUnits = m10s[tensDigit]!! + m1to19[this % 10]!!
            if (this < 100) {
                return tensAndUnits
            }
            val hundredsDigit = (this / 100) % 10
            val hundredsTensAndUnits = m100s[hundredsDigit]!! + tensAndUnits
            if (this < 1000)
                return hundredsTensAndUnits
            val thousandsDigit = (this / 1000) % 100
            return m1to19[thousandsDigit]!! +"׳"+ hundredsTensAndUnits
        }

    }
}
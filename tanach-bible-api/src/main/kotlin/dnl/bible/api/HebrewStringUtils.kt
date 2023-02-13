package dnl.bible.api

import dnl.bible.api.HebrewCharacterUtils.Companion.isFinalLetter
import dnl.bible.api.HebrewCharacterUtils.Companion.isMNZPH
import dnl.bible.api.HebrewCharacterUtils.Companion.toFinal
import dnl.bible.api.HebrewCharacterUtils.Companion.toNonFinal

class HebrewStringUtils {
    companion object {
        fun toFinalChars(s: String): String {
            if(!hasMNZPH(s)) return s
            val sb = StringBuilder()
            s.forEach { if (isMNZPH(it)) sb.append(toFinal(it)) else sb.append(it) }
            return sb.toString()
        }

        fun toNonFinalChars(s: String): String {
            val sb = StringBuilder()
            s.forEach { if (isFinalLetter(it)) sb.append(toNonFinal(it)) else sb.append(it) }
            return sb.toString()
        }

        fun hasFinalChars(s: String): Boolean {
            s.forEach { if (isFinalLetter(it)) return true }
            return false
        }

        fun hasMNZPH(s: String): Boolean {
            s.forEach { if (isMNZPH(it)) return true }
            return false
        }
    }
}
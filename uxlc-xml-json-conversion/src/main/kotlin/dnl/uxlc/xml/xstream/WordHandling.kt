package dnl.uxlc.xml.xstream

import dnl.bible.api.HebrewCharacterUtils
import java.lang.StringBuilder

interface WordProcessing {
    fun process(s: String): String
}

interface WordAssert {
    val message: String
    fun assert(s: String): Boolean
}

class Remove0x5 : WordProcessing {
    override fun process(s: String): String {
        return s.replace("\u0005", "")
    }
}

class OnlyHebrewLetters : WordProcessing {
    override fun process(s: String): String {
        val sb = StringBuilder()
        for (c in s) {
            if (HebrewCharacterUtils.isHebrewLetter(c)) sb.append(c)
        }
        return sb.toString()
    }
}

class OnlyHebrew : WordProcessing {
    override fun process(s: String): String {
        val sb = StringBuilder()
        for (c in s) {
            if (HebrewCharacterUtils.isHebrewChar(c)) sb.append(c)
        }
        return sb.toString()
    }
}

class WordProcessingStack(private vararg val wProcs: WordProcessing) : WordProcessing {
    override fun process(s: String): String {
        var s1 = s
        wProcs.forEach { s1 = it.process(s1) }
        return s1
    }
}

class WordAssertionStack(private vararg val assertions: WordAssert) {

    fun applyAsserts(s: String, chapterIndex: Int, verseIndex: Int, wordIndex: Int) {
        assertions.forEach {
            if (!it.assert(s)) {
                println("${it.message} > chapter:$chapterIndex, verse:$verseIndex word:$wordIndex  > $s")
            }
        }
    }
}

class HasNonHebrewAssertion : WordAssert {
    override val message = "Has non hebrew"

    override fun assert(s: String): Boolean {
        for (c in s) {
            if (!HebrewCharacterUtils.isHebrewChar(c)) {
                return false
            }
        }
        return true
    }
}
package dnl.bible.rendering

import dnl.bible.TraversalResult
import dnl.bible.api.TextDirective
import dnl.bible.api.Verse
import dnl.bible.api.toStringHeb
import dnl.bible.json.BibleLoader
import dnl.bible.verse4name.WordGimatry
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset


class WordGematriaToHtml {
}


fun main() {
    val bible = BibleLoader.loadBible(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-1.2.zip")
    )
    val word = "דניאל"
    val wordGimatry = WordGimatry(bible, word)
    wordGimatry.process()

    val groupedByWord: MutableMap<String, MutableList<TraversalResult>> =
        wordGimatry.results.groupByTo(mutableMapOf()) { it.wordWithDiacritics }


    val s = createHTML().html {
        attributes["dir"] = "rtl"
        head {
            title { +"תוצאות עבור \"$word\":" }
        }
        body {
            groupedByWord.entries.forEach {
                h1 { +it.key }
                ul {
                    it.value.forEach {
                        val v = bible.getVerse(it.wordLocation)
                        val words = v.getWords()
                        li {
                            +"${it.wordLocation.toStringHeb()}:"
                            words.forEachIndexed { index, s ->
                                if (index > 0) +" "
                                if (index == it.wordLocation.wordIndex) {
                                    b { +s }
                                } else {
                                    +s
                                }
                            }
                        }
                    }
                }
            }

        }
    }.toString().trim()

    val file = File("./products/results.html")
    FileUtils.write(
        file,
        "<meta charset=\"utf-8\">\n$s",
        Charset.forName("UTF-8")
    )
    //HtmlToPdf.convert(s, File("./products/results.pdf"))
}


//fun Verse.getWordCharIndexes(wordIndex: Int, directive: TextDirective = TextDirective.SIMPLE): IntRange {
//    val words = getWords(directive)
//    var charIndex = 0
//    for (i in 0 until wordIndex) {
//        charIndex += (words[i].length)
//    }
//    charIndex += (wordIndex - 1)
//    val start = charIndex
//    val end = start + words[wordIndex].length
//    return IntRange(start, end)
//}

fun Verse.markWord(wordIndex: Int, directive: TextDirective = TextDirective.SIMPLE, htmlMark: String): String {
    val words = getWords(directive)
    return StringBuilder().apply {
        words.forEachIndexed { index, s ->
            if (index > 0)
                append(' ')
            if (index == wordIndex) {
                append('<')
                append(htmlMark)
                append('>')
            }
            append(s)
            if (index == wordIndex) {
                append('<')
                append(htmlMark)
                append('/')
                append('>')
            }
        }
    }.toString()
}


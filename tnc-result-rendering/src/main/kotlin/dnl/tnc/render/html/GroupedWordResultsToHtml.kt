package dnl.tnc.render.html

import dnl.bible.api.Bible
import dnl.bible.api.TextDirective
import dnl.bible.api.Verse
import dnl.bible.api.toStringHeb
import dnl.tnc.queries.GroupedWordResults
import dnl.tnc.queries.WordResults
import kotlinx.html.*
import kotlinx.html.stream.createHTML


class GroupedWordResultsToHtml(
    val queryWord: String,
    val groupedResults: GroupedWordResults,
    val bible: Bible
) {
    fun renderHTML(hiddenDetails: Boolean): String {
        return createHTML().html {
            attributes["dir"] = "rtl"
            val titleText = "תוצאות עבור"
            head {
                title { +"$titleText \"$queryWord\":" }
            }
            body {
                h1 { +"${groupedResults.totalNumOfResults} $titleText \"$queryWord\":" }
                groupedResults.results.forEach {
                    h2 { +"${it.word} [${it.wordLocations.size}]" }
                    if (hiddenDetails) {
                        details {
                            summary { +("כל התוצאות") }
                            div {
                                renderWordResultsAsList(it)
                            }
                        }
                    } else {
                        div {
                            renderWordResultsAsList(it)
                        }
                    }

                }

            }
        }.toString().trim()
    }

    private fun DIV.renderWordResultsAsList(it: WordResults) {
        ul {
            it.wordLocations.forEach {
                val v = bible.getVerse(it.verseLocation)
                val words = v.getWords()
                li {
                    +"${it.verseLocation.toStringHeb()}:"
                    words.forEachIndexed { index, s ->
                        if (index > 0) +" "
                        if (index == it.wordIndex) {
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


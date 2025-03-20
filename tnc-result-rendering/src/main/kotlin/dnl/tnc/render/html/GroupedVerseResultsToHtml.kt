package dnl.tnc.render.html

import dnl.bible.api.Bible
import dnl.bible.api.TextDirective
import dnl.bible.api.toStringHeb
import dnl.tnc.queries.GroupedByBookVerseResults
import dnl.tnc.queries.VerseResultsInBook
import kotlinx.html.*
import kotlinx.html.stream.createHTML


class GroupedVerseResultsToHtml(
    val queryWord: String,
    val groupedResults: GroupedByBookVerseResults,
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
                    h2 { +"${it.book.hebrewName} [${it.results.size}]" }
                    if (hiddenDetails) {
                        details {
                            summary { +("כל התוצאות") }
                            div {
                                renderResultsAsList(it)
                            }
                        }
                    } else {
                        div {
                            renderResultsAsList(it)
                        }
                    }

                }

            }
        }.toString().trim()
    }

    private fun DIV.renderResultsAsList(it: VerseResultsInBook) {
        ul {
            it.results.forEach {
                val v = bible.getVerse(it.verseLocation)
                li {
                    +v.getText(TextDirective.DIACRITICS)
                    +" ${it.verseLocation.toStringHeb()}"
                }
            }
        }
    }
}


package dnl.bible.rendering

import dnl.bible.TraversalResult
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
                        li { +"${it.verseLocation.toStringHeb()}: ${bible.getVerse(it.verseLocation).getText()}" }
                    }
                }
            }

        }
    }.toString().trim()

    FileUtils.write(
        File("./products/results.html"),
        "<meta charset=\"utf-8\">\n$s",
        Charset.forName("UTF-8"))
}
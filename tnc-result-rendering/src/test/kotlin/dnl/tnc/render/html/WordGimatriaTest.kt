package dnl.tnc.render.html

import dnl.bible.api.verseIterator
import dnl.bible.json.bible
import dnl.tnc.assertTestsOutputDirExists
import dnl.tnc.queries.SingleWordQueries
import dnl.tnc.testsOutputDir
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset
import kotlin.test.BeforeTest
import kotlin.test.Test

class WordGimatriaTest {

    @BeforeTest
    fun setup() {
        assertTestsOutputDirExists()
    }

    @Test
    fun test() {
        val word = "דנ"
        val groupedResults = SingleWordQueries().queryForSameGimatria(bible.verseIterator(), word)

        val s = GroupedWordResultsToHtml(word, groupedResults, bible).renderHTML(false)

        val file = File(testsOutputDir, "results.html")
        FileUtils.write(
            file,
            "<meta charset=\"utf-8\">\n$s",
            Charset.forName("UTF-8")
        )
    }
}
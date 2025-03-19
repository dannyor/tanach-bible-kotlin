package dnl.tnc.render.html

import dnl.bible.json.BibleLoader
import dnl.tnc.assertTestsOutputDirExists
import dnl.tnc.queries.SingleWordQueries
import dnl.tnc.testsOutputDir
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset
import kotlin.test.BeforeTest

class WordGimatriaTest {

    @BeforeTest
    fun setup() {
        assertTestsOutputDirExists()
    }

    fun test() {
        val bible = BibleLoader.loadBible(
            File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-1.2.zip")
        )
        val word = "אילת"
        val groupedResults = SingleWordQueries().queryForSameGimatria(bible, word)

        val s = GroupedWordResultsToHtml(word, groupedResults, bible).renderHTML(false)

        val file = File(testsOutputDir, "results.html")
        FileUtils.write(
            file,
            "<meta charset=\"utf-8\">\n$s",
            Charset.forName("UTF-8")
        )
    }
}
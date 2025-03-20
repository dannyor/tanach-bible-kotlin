package dnl.tnc.render.html

import dnl.bible.api.verseIterator
import dnl.bible.json.bible
import dnl.tnc.assertTestsOutputDirExists
import dnl.tnc.queries.SingleWordQueries
import dnl.tnc.queries.VerseQueries
import dnl.tnc.testsOutputDir
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset
import kotlin.test.BeforeTest
import kotlin.test.Test

class VerseForNameTest {

    @BeforeTest
    fun setup() {
        assertTestsOutputDirExists()
    }

    @Test
    fun test() {
        val word = "אילת"
        val groupedResults = VerseQueries().verseForNameStartAndEnd(word, bible.verseIterator())

        val s = GroupedVerseResultsToHtml(word, groupedResults, bible).renderHTML(false)

        val file = File(testsOutputDir, "results.html")
        FileUtils.write(
            file,
            "<meta charset=\"utf-8\">\n$s",
            Charset.forName("UTF-8")
        )
    }
}
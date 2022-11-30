package dnl.bible.heblang.word.categorization

import dnl.bible.json.BibleLoader
import java.io.File

class CategorizationCli {

}

fun main() {
    val bible = BibleLoader.loadCombined(
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip"),
        File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-nikud_and_teamim-1.1.zip")
    )

//    bible.
//
//    do {
//
//
//
//        val readVal = readLine()
//        println("readVal=$readVal")
//    } while (readVal != "exit")
}
package dnl.uxlc.xml.xstream

import dnl.bible.json.v2.JsonPersistency
import java.io.File
import java.io.FileReader


fun main() {
    println(System.getProperty("user.dir"))
    val xmlParser = XStreamConversion()
    val book = xmlParser.convert(FileReader("./uxlc-xml-json-conversion/xml-output/uxlc-1.2/01.Genesis.xml")) as dnl.bible.json.v2.Book
    println(book.numOfChapters)

    val jsonPersistency = JsonPersistency()
    jsonPersistency.write(File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/01.Genesis.json"), book)
}
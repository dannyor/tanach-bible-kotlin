package dnl.bible.rendering

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileOutputStream


object HtmlToPdf {

    fun convert(html:String, outputPdf:File){
//        val html = FileUtils.readFileToString(f, "UTF-8")
        FileOutputStream(outputPdf).use { os ->
            val builder: PdfRendererBuilder = PdfRendererBuilder()
            builder.useFastMode()
//            builder.useFont(File("Z:/projects/daniyel/tanach-bible-kotlin/fonts/ShlomoSemiStam.ttf"), "Arial Unicode MS")
//            builder.useFont(File("Z:/projects/daniyel/tanach-bible-kotlin/fonts/l_10646.ttf"), "Arial Unicode MS")
            builder.useFont(File("Z:/projects/daniyel/tanach-bible-kotlin/fonts/arial.ttf.ttf"), "Arial Unicode MS")
            builder.defaultTextDirection(BaseRendererBuilder.TextDirection.LTR)
//        builder.withUri("file:///Users/me/Documents/pdf/in.htm")
            builder.withHtmlContent(html, null)
            builder.toStream(os)
            builder.run()
        }
    }

}

fun main() {
//    HtmlToPdf.convert(
//        File("./products/results.html"),
//        File("./products/results.pdf")
//    )
}
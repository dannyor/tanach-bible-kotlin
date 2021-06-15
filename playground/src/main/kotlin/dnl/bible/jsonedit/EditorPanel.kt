package dnl.bible.jsonedit

import java.awt.BorderLayout
import javax.swing.JEditorPane
import javax.swing.JPanel
import javax.swing.JScrollPane

class EditorPanel : JPanel() {
    private val scrollPane = JScrollPane()
    val editorPane = JEditorPane()

    init {
        layout = BorderLayout()
        scrollPane.setViewportView(editorPane)
        add(scrollPane, BorderLayout.CENTER)
    }
}
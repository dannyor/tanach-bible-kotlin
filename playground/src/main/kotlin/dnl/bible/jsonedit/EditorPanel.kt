package dnl.bible.jsonedit

import java.awt.BorderLayout
import java.awt.ComponentOrientation
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextPane


class EditorPanel : JPanel() {
    private val scrollPane = JScrollPane()
    val editorPane = JTextPane()

    init {
        layout = BorderLayout()
        scrollPane.setViewportView(editorPane)
        add(scrollPane, BorderLayout.CENTER)

        editorPane.componentOrientation = ComponentOrientation.RIGHT_TO_LEFT

    }
}
package dnl.bible.jsonedit

import java.awt.BorderLayout
import java.awt.ComponentOrientation
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextPane


class EditorPanel : JPanel() {
    private val scrollPane = JScrollPane()
    val editorPane = JTextPane()
    val checkBox = JCheckBox("Full Symbols")
    init {
        layout = BorderLayout()
        scrollPane.setViewportView(editorPane)
        add(scrollPane, BorderLayout.CENTER)
        val jPanel = JPanel()
        jPanel.add(checkBox)
        add(jPanel, BorderLayout.NORTH)

        editorPane.componentOrientation = ComponentOrientation.RIGHT_TO_LEFT
    }

    fun isFullSymbols() : Boolean = checkBox.isSelected
}
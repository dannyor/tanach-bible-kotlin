package dnl.bible.jsonedit

import dnl.bible.api.Bible
import dnl.bible.api.Chapter
import dnl.bible.api.TextDirective
import java.awt.BorderLayout
import java.awt.ComponentOrientation
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.event.ActionEvent
import java.io.File
import java.util.logging.Level
import java.util.logging.Logger
import javax.swing.*
import javax.swing.event.TreeSelectionEvent
import javax.swing.filechooser.FileSystemView
import javax.swing.tree.DefaultMutableTreeNode


class Editor1 : JFrame("") {
    private val menuBar = JMenuBar()
    private val fileMenu = JMenu()
    private val openMenuItem = JMenuItem()
    private val saveMenuItem = JMenuItem()
    private val exitMenuItem = JMenuItem()
    private val editorPanel = EditorPanel()

    private val splitPane = JSplitPane()
    private val bibleChaptersTree = JTree()
    lateinit var bible: Bible

    private lateinit var seletedChapter: Chapter

    init {
        menuBar.add(fileMenu)
        defaultCloseOperation = EXIT_ON_CLOSE

        fileMenu.setMnemonic('f')
        fileMenu.text = "File"

        openMenuItem.setMnemonic('o')
        openMenuItem.text = "Open"
        openMenuItem.addActionListener { evt -> openMenuItemActionPerformed(evt) }
        fileMenu.add(openMenuItem)

        saveMenuItem.setMnemonic('s')
        saveMenuItem.text = "Save"
        fileMenu.add(saveMenuItem)
        exitMenuItem.setMnemonic('x')
        exitMenuItem.text = "Exit"
        exitMenuItem.addActionListener { evt -> exitMenuItemActionPerformed(evt) }
        fileMenu.add(exitMenuItem)

        this.jMenuBar = menuBar
        bibleChaptersTree.componentOrientation = ComponentOrientation.RIGHT_TO_LEFT
        splitPane.rightComponent = JScrollPane(bibleChaptersTree)
        splitPane.leftComponent = editorPanel
        add(splitPane, BorderLayout.CENTER)
        pack()
        splitPane.dividerLocation = 600

        bibleChaptersTree.addTreeSelectionListener { t -> handleTreeSelection(t) }
        editorPanel.checkBox.addItemListener {
            displaySelectedChapter()
        }
    }

    private fun handleTreeSelection(t: TreeSelectionEvent?) {
        val node = bibleChaptersTree.lastSelectedPathComponent
        if (node is ChapterNode) {
            seletedChapter = node.chapter
            displaySelectedChapter()
        }
    }

    private fun displaySelectedChapter() {
        val chapterAsText = getChapterAsText(seletedChapter)
        editorPanel.editorPane.text = chapterAsText
    }

    private fun openMenuItemActionPerformed(evt: ActionEvent?) {
        val jfc = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)

        val returnValue = jfc.showOpenDialog(null)
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            val selectedFile: File = jfc.selectedFile
            println(selectedFile.absolutePath)
        }
    }

    private fun exitMenuItemActionPerformed(evt: ActionEvent?) {
        TODO("Not yet implemented")
    }

    fun openFile(file: File) {
        val full = File(file.parentFile, "bible-1.2.zip")
        if (!full.exists()) {
            throw IllegalArgumentException()
        }
        bibleChaptersTree.model = BibleTreeModel(bible, DefaultMutableTreeNode(bible))
    }

    private fun getChapterAsText(chap: Chapter): String {
        val sb = StringBuilder()

        chap.getAllVerses().forEach {
            sb.append(if (editorPanel.isFullSymbols()) it.getText(TextDirective.DIACRITICS) else it.getText(TextDirective.SIMPLE))
            sb.append('\n')
        }
        return sb.toString()
    }
}

fun main() {
    try {
        for (info in UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus" == info.name) {
                UIManager.setLookAndFeel(info.className)
                break
            }
        }
    } catch (ex: ClassNotFoundException) {
        Logger.getLogger(Editor1::class.java.name).log(Level.SEVERE, null, ex)
    } catch (ex: InstantiationException) {
        Logger.getLogger(Editor1::class.java.name).log(Level.SEVERE, null, ex)
    } catch (ex: IllegalAccessException) {
        Logger.getLogger(Editor1::class.java.name).log(Level.SEVERE, null, ex)
    } catch (ex: UnsupportedLookAndFeelException) {
        Logger.getLogger(Editor1::class.java.name).log(Level.SEVERE, null, ex)
    }

    EventQueue.invokeLater {
        val editor1 = Editor1()
        editor1.size = Dimension(1000, 1000)
        editor1.openFile(File("./uxlc-xml-json-conversion/json-output/uxlc-1.2/bible-just_letters-1.1.zip"))
        editor1.isVisible = true
    }
}
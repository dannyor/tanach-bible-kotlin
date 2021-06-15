package dnl.bible.jsonedit

import dnl.bible.api.Bible
import dnl.bible.api.Chapter
import dnl.bible.json.BibleLoader
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.event.ActionEvent
import java.io.File
import java.lang.StringBuilder
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

    init {
        menuBar.add(fileMenu)
        defaultCloseOperation = javax.swing.WindowConstants.EXIT_ON_CLOSE

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
        splitPane.rightComponent = editorPanel
        splitPane.leftComponent = JScrollPane(bibleChaptersTree)
        add(splitPane, BorderLayout.CENTER)
        pack()
        splitPane.dividerLocation = 350

        bibleChaptersTree.addTreeSelectionListener { t -> handleTreeSelection(t) }
    }

    private fun handleTreeSelection(t: TreeSelectionEvent?) {
        val node = bibleChaptersTree.lastSelectedPathComponent
        if(node is ChapterNode) {
            val chapterAsText = getChapterAsText((node as ChapterNode).chapter)
            editorPanel.editorPane.text = chapterAsText
        }
    }

    private fun openMenuItemActionPerformed(evt: ActionEvent?) {
        val jfc = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)

        val returnValue = jfc.showOpenDialog(null)
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            val selectedFile: File = jfc.selectedFile
            System.out.println(selectedFile.getAbsolutePath())
        }
    }

    private fun exitMenuItemActionPerformed(evt: ActionEvent?) {
        TODO("Not yet implemented")
    }

    fun openFile(file: File) {
        bible = BibleLoader.loadFrom(file)
        bibleChaptersTree.model = BibleTreeModel(bible, DefaultMutableTreeNode(bible))
    }

    fun getChapterAsText(chap:Chapter) : String {
        val sb = StringBuilder()
        var verse = chap.getVerse(1)

        while (verse.hasNext()) {
            sb.append(verse.getText())
            verse = verse.next()
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
        editor1.size = Dimension(1000,1000)
        editor1.openFile(File("../json-bible-hebrew/bible-json-files/bible-just_letters-1.0.zip"))
        editor1.isVisible = true
    }
}
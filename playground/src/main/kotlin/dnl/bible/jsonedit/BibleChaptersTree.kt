package dnl.bible.jsonedit

import dnl.bible.api.BibleBook
import dnl.bible.api.Bible
import dnl.bible.api.Book
import dnl.bible.api.Chapter
import javax.swing.JTree
import javax.swing.event.TreeModelListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeModel
import javax.swing.tree.TreeNode
import javax.swing.tree.TreePath

class BibleChaptersTree(
    private val bible:Bible
    ) : JTree(BibleTreeModel(bible, DefaultMutableTreeNode(bible)))


class BibleTreeModel(val bible:Bible, val rootNode: TreeNode) : TreeModel {
    override fun getRoot(): Any {
        return rootNode
    }

    override fun getChild(parent: Any?, index: Int): Any {
        if (parent == root) {
            val book = bible.getBook(BibleBook.values()[index])
            return BookNode(book)
        }
        val bookNode = parent as BookNode
        return ChapterNode(bookNode.book.getChapter(index+1)) // bible chapters are 1 based
    }

    override fun getChildCount(parent: Any?): Int {
        if (parent == root) {
            return BibleBook.values().size
        }
        val bookNode = parent as BookNode
        return bookNode.book.getNumOfChapters()
    }

    override fun isLeaf(node: Any?): Boolean {
        return node is ChapterNode
    }

    override fun valueForPathChanged(path: TreePath?, newValue: Any?) {

    }

    override fun getIndexOfChild(parent: Any?, child: Any?): Int {
        if (parent is ChapterNode) {
            return (parent as ChapterNode).chapter.getIndex()
        }
        if (parent is BookNode) {
            val book = (parent as BookNode).book
            return BibleBook.valueOf(book.getName()).ordinal
        }
        return 0
    }

    override fun addTreeModelListener(l: TreeModelListener?) {

    }

    override fun removeTreeModelListener(l: TreeModelListener?) {

    }

}

data class BookNode(val book: Book) : DefaultMutableTreeNode(book.getName()) {
    override fun toString(): String {
        return book.getName()
    }
}
data class ChapterNode(val chapter: Chapter) : DefaultMutableTreeNode(chapter.getIndex()) {
    override fun toString(): String {
        return chapter.getIndex().toString()
    }
}

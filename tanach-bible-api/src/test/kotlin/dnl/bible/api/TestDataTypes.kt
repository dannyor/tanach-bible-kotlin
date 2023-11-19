package dnl.bible.api


class TestVerse(val dtext: String) : Verse {
    override fun getParent() = TODO()
    override fun getIndex() = 0
    override fun getWords() = dtext.split(" ")
    override fun getText() = dtext
}


package dnl.bible.api


class TestVerse(val dtext: String) : Verse {
    override fun getParent() = TODO()
    override fun getIndex() = 0
    override fun getWords(directive: TextDirective) = dtext.split(" ")
    override fun getText(directive: TextDirective) = dtext
    override fun getLocation(): VerseLocation {
        TODO("Not yet implemented")
    }
}


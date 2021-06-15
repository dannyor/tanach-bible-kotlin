package dnl.bible.api.v2

//class VerseRangeIterator(private val book: Book, private val range: VerseRange) : Iterator<Verse> {
//    private lateinit var current: Verse
//
//    override fun hasNext(): Boolean {
//        if(!this::current.isInitialized) return true
//        if(current.getParent().getIndex() == range.end.chapterIndex && current.getIndex() == range.end.verseIndex)
//            return false
//        return current.hasNext()
//    }
//
//    override fun next(): Verse {
//        current =
//            if(!this::current.isInitialized)
//            book.getVerse(range.start)
//            else {
//                current.next()
//            }
//        return current
//    }
//}
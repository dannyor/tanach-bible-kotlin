package dnl.tnc.queries

object ToString {
    fun toString(groupedWordResults: GroupedWordResults): String {
        val sb = StringBuilder()
        groupedWordResults.results
        return sb.toString()
    }
}
package dnl.tnc.queries

import dnl.bible.api.toStringHeb
import dnl.tnc.bible

object ToString {
    fun toString(groupedWordResults: GroupedWordResults): String {
        val sb = StringBuilder("${groupedWordResults.totalNumOfResults} results:\n")
        groupedWordResults.results.forEach { result->
            sb.append(result.word)
            sb.append(" (${result.wordLocations.size} results):\n")
            result.wordLocations.forEach {
                sb.append('\t')
                sb.append(it.verseLocation.toStringHeb())
                sb.append(':')
                sb.append(bible.getVerse(it.verseLocation).getText())
                sb.append('\n')
            }

        }
        return sb.toString()
    }
}
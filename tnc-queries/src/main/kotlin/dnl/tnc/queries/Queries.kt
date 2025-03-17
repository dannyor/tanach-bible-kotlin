package dnl.tnc.queries

import dnl.bible.api.VerseLocation
import kotlinx.serialization.Serializable

@Serializable
data class VerseForWordQuery(
    val word:String
)



data class VerseResult(
    val verseLocation: VerseLocation,
    val relevantWords: List<Int>,
    val relevantCharacters: List<Int>
)
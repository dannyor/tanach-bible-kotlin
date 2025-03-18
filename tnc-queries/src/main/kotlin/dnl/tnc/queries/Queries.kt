package dnl.tnc.queries

import dnl.bible.api.VerseInnerLocation
import dnl.bible.api.VerseLocation
import dnl.bible.api.VerseWithCharLocations
import dnl.bible.api.WordLocation
import kotlinx.serialization.Serializable

@Serializable
data class VerseForWordQuery(
    val word: String
)

@Serializable
data class VerseResult(
    override val verseLocation: VerseLocation,
    val relevantWords: List<Int>,
    override val innerLocations: List<VerseInnerLocation>
) : VerseWithCharLocations

@Serializable
data class WordResult(
    val word: String,
    val wordWithDiacritics: String,
    val wordLocation: WordLocation
)
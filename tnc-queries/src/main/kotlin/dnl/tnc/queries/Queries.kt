package dnl.tnc.queries

import dnl.bible.api.BibleBook
import dnl.bible.api.VerseInnerLocations
import dnl.bible.api.WordLocation
import kotlinx.serialization.Serializable

@Serializable
data class VerseForWordQuery(
    val word: String
)

@Serializable
data class WordResult(
    val word: String,
    val wordWithDiacritics: String,
    val wordLocation: WordLocation
)

@Serializable
data class WordResults(
    val word: String,
    val wordWithDiacritics: String,
    val wordLocations: List<WordLocation>
)

@Serializable
data class GroupedWordResults(
    val totalNumOfResults:Int,
    val results: List<WordResults>
)

@Serializable
data class VerseResultsInBook(
    val book: BibleBook,
    val results: List<VerseInnerLocations>
)
@Serializable
data class GroupedByBookVerseResults(
    val totalNumOfResults:Int,
    val results: List<VerseResultsInBook>
)

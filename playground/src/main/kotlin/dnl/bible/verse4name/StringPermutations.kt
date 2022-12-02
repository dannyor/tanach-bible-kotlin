package dnl.bible.verse4name

class StringPermutations {
}

fun String.permute(result: String = ""): List<String> =
    if (isEmpty()) listOf(result) else flatMapIndexed { i, c -> removeRange(i, i + 1).permute(result + c) }

fun main() {
    println("הדסה".permute().toSet()) // [abc, acb, bac, bca, cab, cba]
}
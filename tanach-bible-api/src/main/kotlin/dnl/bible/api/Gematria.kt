package dnl.bible.api

object Gematria {

    private val char2Int = mapOf(
        HebrewChars.ALEF.char to 1,
        HebrewChars.BET.char to 2,
        HebrewChars.GIMEL.char to 3,
        HebrewChars.DALET.char to 4,
        HebrewChars.HE.char to 5,
        HebrewChars.VAV.char to 6,
        HebrewChars.ZAYIN.char to 7,
        HebrewChars.HET.char to 8,
        HebrewChars.TET.char to 9,
        HebrewChars.YOD.char to 10,
        HebrewChars.KAF.char to 20,
        HebrewChars.LAMED.char to 30,
        HebrewChars.MEM.char to 40,
        HebrewChars.NUN.char to 50,
        HebrewChars.SAMEKH.char to 60,
        HebrewChars.AYIN.char to 70,
        HebrewChars.PE.char to 80,
        HebrewChars.TSADI.char to 90,
        HebrewChars.QOF.char to 100,
        HebrewChars.RESH.char to 200,
        HebrewChars.SHIN.char to 300,
        HebrewChars.TAV.char to 400
    )

    private val otiyotSofiyot2Int = mapOf(
        HebrewChars.FINAL_KAF.char to 500,
        HebrewChars.FINAL_MEM.char to 600,
        HebrewChars.FINAL_NUN.char to 700,
        HebrewChars.FINAL_PE.char to 800,
        HebrewChars.FINAL_TSADI.char to 900
    )

    private val otiyotSofiyot2RegularInt = mapOf(
        HebrewChars.FINAL_KAF.char to 20,
        HebrewChars.FINAL_MEM.char to 40,
        HebrewChars.FINAL_NUN.char to 50,
        HebrewChars.FINAL_PE.char to 80,
        HebrewChars.FINAL_TSADI.char to 90
    )

    fun gematriaOf(char: Char): Int {
        if (char2Int.containsKey(char)) {
            return char2Int[char]!!
        }
        if (otiyotSofiyot2RegularInt.containsKey(char)) {
            return otiyotSofiyot2RegularInt[char]!!
        }
        throw IllegalArgumentException("No mapping for '$char' unicode ${char.getUnicode()}")
    }

    fun gematriaOf(s: String): Int {
        var v = 0
        s.forEach {
            v += gematriaOf(it)
        }
        return v
    }
}




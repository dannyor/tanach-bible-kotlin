package dnl.bible.api

class HebrewCharacterUtils {
    companion object {
        fun isHebrewChar(char:Char) : Boolean {
            return char.toInt() in 0x590..0x5F4
        }

        fun isHebrewLetter(char:Char) : Boolean {
            return char.toInt() in 0x5D0..0x5EA
        }

        fun isHebrewPunctuation(char:Char) : Boolean {
            return char.toInt() in 0x5B0..0x5BC || char.toInt() in arrayOf(0x5C1, 0x5C2, 0x5C7, 0x5F3, 0x5F4)
        }
    }
}

fun main(){
    println(HebrewCharacterUtils.isHebrewChar('\u0591'))
}

enum class HebrewChars(val char:Char) {
    ACCENT_ETNAH                   ('\u0591'),
    ACCENT_SEGOL                   ('\u0592'),
    ACCENT_SHALSHELET              ('\u0593'),
    ACCENT_ZAKEF_KATAN             ('\u0594'),
    ACCENT_ZAKEF_GADOL             ('\u0595'),
    ACCENT_TIPEHA                  ('\u0596'),
    ACCENT_REVIA                   ('\u0597'),
    ACCENT_ZARQA                   ('\u0598'),
    ACCENT_PASHTA                  ('\u0599'),
    ACCENT_YETIV                   ('\u059A'),
    ACCENT_TEVIR                   ('\u059B'),
    ACCENT_GERESH                  ('\u059C'),
    ACCENT_GERESH_MUKDAM           ('\u059D'),
    ACCENT_GERSHAYIM               ('\u059E'),
    ACCENT_QARNEY_PARA             ('\u059F'),
    ACCENT_TELISHA_GEDOLA          ('\u05A0'),
    ACCENT_PAZER                   ('\u05A1'),
    ACCENT_ATNAH_HAFUH             ('\u05A2'),
    ACCENT_MUNAH                   ('\u05A3'),
    ACCENT_MAHAPAH                 ('\u05A4'),
    ACCENT_MERHA                   ('\u05A5'),
    ACCENT_MERHA_QFULA             ('\u05A6'),
    ACCENT_DARGA                   ('\u05A7'),
    ACCENT_QADMA                   ('\u05A8'),
    ACCENT_TELISHA_QETANA          ('\u05A9'),
    ACCENT_YERAH_BEN_YOMO          ('\u05AA'),
    ACCENT_OLE                     ('\u05AB'),
    ACCENT_ILUY                    ('\u05AC'),
    ACCENT_DEHI                    ('\u05AD'),
    ACCENT_ZINOR                   ('\u05AE'),
    MASORA_CIRCLE                  ('\u05AF'),
    NIKUD_SHEVA                    ('\u05B0'),
    NIKUD_HATAF_SEGOL              ('\u05B1'),
    NIKUD_HATAF_PATAH              ('\u05B2'),
    NIKUD_HATAF_QAMATS             ('\u05B3'),
    NIKUD_HIRIK                    ('\u05B4'),
    NIKUD_TSEREI                   ('\u05B5'),
    NIKUD_SEGOL                    ('\u05B6'),
    NIKUD_PATAH                    ('\u05B7'),
    NIKUD_KAMATS                   ('\u05B8'),
    NIKUD_HOLAM                    ('\u05B9'),
    NIKUD_HOLAM_HASER              ('\u05BA'),
    NIKUD_KUBUTS                   ('\u05BB'),
    NIKUD_MAPIQ                    ('\u05BC'),
    METEG                          ('\u05BD'),
    MAQAF                          ('\u05BE'),
    RAFE                           ('\u05BF'),
    PASEQ                          ('\u05C0'),
    NIKUD_SHIN_DAGESH              ('\u05C1'),
    NIKUD_SIN_DAGESH               ('\u05C2'),
    SOF_PASUQ                      ('\u05C3'),
    UPPER_DOT                      ('\u05C4'),
    LOWER_DOT                      ('\u05C5'),
    NUN_HAFUKHA                    ('\u05C6'),
    NIKUD_KAMATS_KATAN             ('\u05C7'),
    ALEF                           ('\u05D0'),
    BET                            ('\u05D1'),
    GIMEL                          ('\u05D2'),
    DALET                          ('\u05D3'),
    HE                             ('\u05D4'),
    VAV                            ('\u05D5'),
    ZAYIN                          ('\u05D6'),
    HET                            ('\u05D7'),
    TET                            ('\u05D8'),
    YOD                            ('\u05D9'),
    FINAL_KAF                      ('\u05DA'),
    KAF                            ('\u05DB'),
    LAMED                          ('\u05DC'),
    FINAL_MEM                      ('\u05DD'),
    MEM                            ('\u05DE'),
    FINAL_NUN                      ('\u05DF'),
    NUN                            ('\u05E0'),
    SAMEKH                         ('\u05E1'),
    AYIN                           ('\u05E2'),
    FINAL_PE                       ('\u05E3'),
    PE                             ('\u05E4'),
    FINAL_TSADI                    ('\u05E5'),
    TSADI                          ('\u05E6'),
    QOF                            ('\u05E7'),
    RESH                           ('\u05E8'),
    SHIN                           ('\u05E9'),
    TAV                            ('\u05EA'),
    YOD_TRIANGLE                   ('\u05EF'),
    YIDDISH_DOUBLE_VAV             ('\u05F0'),
    YIDDISH_VAV_YOD                ('\u05F1'),
    YIDDISH_DOUBLE_YOD             ('\u05F2'),
    PUNCTUATION_GERESH             ('\u05F3'),
    PUNCTUATION_GERSHAYIM          ('\u05F4'),

}
package dnl.bible.api

import dnl.bible.api.BibleBook.*
import dnl.bible.api.VerseRangeFactory.newVerseRange
import java.lang.IllegalArgumentException


enum class BibleGroups(val groupName: String, vararg val books: BibleBook) {
    TORA("תורה", GENESIS, EXODUS, LEVITICUS, NUMBERS, DEUTERONOMY),
    NEVIIM(
        "נביאים",
        JOSHUA, JUDGES, SAMUEL_1, SAMUEL_2, KINGS_1, KINGS_2,
        ISAIAH, JEREMIAH, EZEKIEL, HOSEA, JOEL, AMOS, OBADIAH, JONAH, MICAH,
        NAHUM, HABAKKUK, ZEPHANIAH, HAGGAI, ZECHARIAH, MALACHI
    ),
    KETUVIM(
        "כתובים",
        PSALMS, PROVERBS, JOB, SONG_OF_SONGS, RUTH, LAMENTATIONS,
        ECCLESIASTES, ESTHER, DANIEL, EZRA, NEHEMIAH, CHRONICLES_1, CHRONICLES_2
    )
}

enum class HumashEnum(val bibleBook: BibleBook) {
    GENESIS(BibleBook.GENESIS),
    EXODUS(BibleBook.EXODUS),
    LEVITICUS(BibleBook.LEVITICUS),
    NUMBERS(BibleBook.NUMBERS),
    DEUTERONOMY(BibleBook.DEUTERONOMY)
}

enum class BibleBook(val englishName: String, val hebrewName: String, vararg parasha: ParashaEnum) {
    GENESIS("Genesis", "בראשית"),
    EXODUS("Exodus", "שמות"),
    LEVITICUS("Leviticus", "ויקרא"),
    NUMBERS("Numbers", "במדבר"),
    DEUTERONOMY("Deuteronomy", "דברים"),
    JOSHUA("Joshua", "יהושע"),
    JUDGES("Judges", "שופטים"),
    SAMUEL_1("Samuel_1", "שמואל א"),
    SAMUEL_2("Samuel_2", "שמואל ב"),
    KINGS_1("Kings_1", "מלכים א"),
    KINGS_2("Kings_2", "מלכים ב"),
    ISAIAH("Isaiah", "ישעיה"),
    JEREMIAH("Jeremiah", "ירמיה"),
    EZEKIEL("Ezekiel", "יחזקאל"),
    HOSEA("Hosea", "הושע"),
    JOEL("Joel", "יואל"),
    AMOS("Amos", "עמוס"),
    OBADIAH("Obadiah", "עובדיה"),
    JONAH("Jonah", "יונה"),
    MICAH("Micah", "מיכה"),
    NAHUM("Nahum", "נחום"),
    HABAKKUK("Habakkuk", "חבקוק"),
    ZEPHANIAH("Zephaniah", "צפניה"),
    HAGGAI("Haggai", "חגי"),
    ZECHARIAH("Zechariah", "זכריה"),
    MALACHI("Malachi", "מלאכי"),
    PSALMS("Psalms", "תהילים"),
    PROVERBS("Proverbs", "משלי"),
    JOB("Job", "איוב"),
    SONG_OF_SONGS("Song of Songs", "שיר השירים"),
    RUTH("Ruth", "רות"),
    LAMENTATIONS("Lamentations", "איכה"),
    ECCLESIASTES("Ecclesiastes", "קהלת"),
    ESTHER("Esther", "אסתר"),
    DANIEL("Daniel", "דניאל"),
    EZRA("Ezra", "עזרא"),
    NEHEMIAH("Nehemiah", "נחמיה"),
    CHRONICLES_1("Chronicles_1", "דברי הימים א"),
    CHRONICLES_2("Chronicles_2", "דברי הימים ב");

    companion object {
        fun byHebrewName(hebrewName: String): BibleBook {
            values().forEach {
                if (it.hebrewName == hebrewName) return it
            }
            throw IllegalArgumentException()
        }
        fun byEnglishName(hebrewName: String): BibleBook {
            values().forEach {
                if (it.englishName == hebrewName) return it
            }
            throw IllegalArgumentException()
        }
    }
}

enum class ParashaEnum(val englishName: String, val hebrewName: String, val range: VerseRange) {
    BEREISHIS("Bereishis", "בראשית", newVerseRange("Genesis 1:1–6:8")),
    NOACH("Noach", "נח", newVerseRange("Genesis 6:9–11:32")),
    LECH_LECHA("Lech Lecha", "לך לך", newVerseRange("Genesis 12:1–17:27")),
    VAYERA("Vayera", "וירא", newVerseRange("Genesis 18:1–22:24")),
    CHAYEI_SARAH("Chayei Sarah", "חיי שרה", newVerseRange("Genesis 23:1–25:18")),
    TOLDOS("Toldos", "תולדות", newVerseRange("Genesis 25:19–28:9")),
    VAYEITZEI("Vayeitzei", "ויצא", newVerseRange("Genesis 28:10–32:3")),
    VAYISHLACH("Vayishlach", "וישלח", newVerseRange("Genesis 32:4–36:43")),
    VAYEISHEV("Vayeishev", "וישב", newVerseRange("Genesis 37:1–40:23")),
    MIKETZ("Miketz", "מקץ", newVerseRange("Genesis 41:1–44:17")),
    VAYIGASH("Vayigash", "ויגש", newVerseRange("Genesis 44:18–47:27")),
    VAYECHI("Vayechi", "ויחי", newVerseRange("Genesis 47:28–50:26")),
    SHEMOS("Shemos", "שמות", newVerseRange("Exodus 1:1–6:1")),
    VAERA("Vaera", "וארא", newVerseRange("Exodus 6:2–9:35")),
    BO("Bo", "בא", newVerseRange("Exodus 10:1–13:16")),
    BESHALACH("Beshalach", "בשלח", newVerseRange("Exodus 13:17–17:16")),
    YISRO("Yisro", "יתרו", newVerseRange("Exodus 18:1–20:23")),
    MISHPATIM("Mishpatim", "משפטים", newVerseRange("Exodus 21:1–24:18")),
    TERUMAH("Terumah", "תרומה", newVerseRange("Exodus 25:1–27:19")),
    TETZAVEH("Tetzaveh", "תצוה", newVerseRange("Exodus 27:20–30:10")),
    KI_SISA("Ki Sisa", "כי תשא", newVerseRange("Exodus 30:11–34:35")),
    VAYAKHEL("Vayakhel", "ויקהל", newVerseRange("Exodus 35:1–38:20")),
    PEKUDEI("Pekudei", "פקודי", newVerseRange("Exodus 38:21–40:38")),
    VAYIKRA("Vayikra", "ויקרא", newVerseRange("Leviticus 1:1–5:26")),
    TZAV("Tzav", "צו", newVerseRange("Leviticus 6:1–8:36")),
    SHEMINI("Shemini", "שמיני", newVerseRange("Leviticus 9:1–11:47")),
    TAZRIA("Tazria", "תזריע", newVerseRange("Leviticus 12:1–13:59")),
    METZORAH("Metzorah", "מצורע", newVerseRange("Leviticus 14:1–15:33")),
    ACHAREI_MOS("Acharei Mos", "אחרי מות", newVerseRange("Leviticus 16:1–18:30")),
    KEDOSHIM("Kedoshim", "קדושים", newVerseRange("Leviticus 19:1–20:27")),
    EMOR("Emor", "אמור", newVerseRange("Leviticus 21:1–24:23")),
    BEHAR("Behar", "בהר", newVerseRange("Leviticus 25:1–26:2")),
    BECHUKOSAI("Bechukosai", "בחוקותי", newVerseRange("Leviticus 26:3–27:34")),
    BAMIDBAR("Bamidbar", "במדבר", newVerseRange("Numbers 1:1–4:20")),
    NASO("Naso", "נשא", newVerseRange("Numbers 4:21–7:89")),
    BEHAALOSCHA("Behaaloscha", "בהעלותך", newVerseRange("Numbers 8:1–12:16")),
    SHLACH("Shlach", "שלח", newVerseRange("Numbers 13:1–15:41")),
    KORACH("Korach", "קרח", newVerseRange("Numbers 16:1–18:32")),
    CHUKAS("Chukas", "חקת", newVerseRange("Numbers 19:1–22:1")),
    BALAK("Balak", "בלק", newVerseRange("Numbers 22:2–25:9")),
    PINCHAS("Pinchas", "פנחס", newVerseRange("Numbers 25:10–30:1")),
    MATOS("Matos", "מטות", newVerseRange("Numbers 30:2–32:42")),
    MASEI("Masei", "מסעי", newVerseRange("Numbers 33:1–36:13")),
    DEVARIM("Devarim", "דברים", newVerseRange("Deuteronomy 1:1–3:22")),
    VAESCHANAN("Vaeschanan", "ואתחנן", newVerseRange("Deuteronomy 3:23–7:11")),
    EIKEV("Eikev", "עקב", newVerseRange("Deuteronomy 7:12–11:25")),
    REEH("Reeh", "ראה", newVerseRange("Deuteronomy 11:26–16:17")),
    SHOFTIM("Shoftim", "שופטים", newVerseRange("Deuteronomy 16:18–21:9")),
    KI_SEITZEI("Ki Seitzei", "כי תצא", newVerseRange("Deuteronomy 21:10–25:19")),
    KI_SAVO("Ki Savo", "כי תבוא", newVerseRange("Deuteronomy 26:1–29:8")),
    NETZAVIM("Netzavim", "נצבים", newVerseRange("Deuteronomy 29:9–30:20")),
    VAYEILECH("Vayeilech", "וילך", newVerseRange("Deuteronomy 31:1–31:30")),
    HAAZINU("Haazinu", "האזינו", newVerseRange("Deuteronomy 32:1–32:52")),
    VZOS_HABRACHA("Vzos Habracha", "וזאת הברכה", newVerseRange("Deuteronomy 33:1–34:12"));

    companion object {
        fun byHebrewName(hebrewName: String): BibleBook {
            BibleBook.values().forEach {
                if (it.hebrewName == hebrewName) return it
            }
            throw IllegalArgumentException()
        }
        fun byEnglishName(hebrewName: String): BibleBook {
            BibleBook.values().forEach {
                if (it.englishName == hebrewName) return it
            }
            throw IllegalArgumentException()
        }
    }
}
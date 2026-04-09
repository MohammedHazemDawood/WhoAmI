package com.mhd_07.whoami

data class IdData(
    val gender: Gender,
    val birthPlace: Place,
    val day: Int,
    val month: Int,
    val year: Int,
    val idNumber: String
)

enum class Gender(name: String) {
    MALE("Male"),
    FEMALE("Female")
}

data class Place(
    val code: String,
    val name: String
) {
    companion object {
        val ALL = listOf(
            Place("01", "Cairo"),
            Place("02", "Alexandria"),
            Place("03", "Port Said"),
            Place("04", "Suez"),
            Place("11", "Damietta"),
            Place("12", "Dakahlia"),
            Place("13", "Sharqia"),
            Place("14", "Qalyubia"),
            Place("15", "Kafr El Sheikh"),
            Place("16", "Gharbia"),
            Place("17", "Menoufia"),
            Place("18", "Beheira"),
            Place("19", "Ismailia"),
            Place("21", "Giza"),
            Place("22", "Beni Suef"),
            Place("23", "Faiyum"),
            Place("24", "Minya"),
            Place("25", "Asyut"),
            Place("26", "Sohag"),
            Place("27", "Qena"),
            Place("28", "Aswan"),
            Place("29", "Luxor"),
            Place("88", "Outside Egypt")
        )

        fun fromCode(code: String): Place? = ALL.find { it.code == code }
    }
}
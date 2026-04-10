package com.mhd_07.whoami


//foramt is X - YY - MM - DD - SS - CCC - G - R
class IdAnalyzer() {
    private fun getGender(G : Char): Gender =
        if ((G.digitToInt() and 1) == 1) Gender.MALE else Gender.FEMALE

    private fun getBirthPlace(SS : String): Place? = Place.fromCode(SS)
    private fun getDay(DD : String): Int = DD.toInt()
    private fun getMonth(MM : String): Int = MM.toInt()
    private fun getYear(X : Char, YY : String): Int = ((if (X == '3') "20" else if (X == '2') "19" else "") + YY).toInt()

    fun analyze(id: String): Result {
        var X = ' '
        var YY = ""
        var MM = ""
        var DD = ""
        var SS = ""
        var CCC = ""
        var G = ' '
        var R = ' '
        if (id.length == 14) {
            X = id.first()
            YY = id.substring(1, 3)
            MM = id.substring(3, 5)
            DD = id.substring(5, 7)
            SS = id.substring(7, 9)
            CCC = id.substring(9, 12)
            G = id[id.length - 2]
            R = id.last()
        }
        return if (id.length == 14 && id.all { it.isDigit() } && (X == '2' || X == '3') && DD.toInt() in 1..31 && MM.toInt() in 1..12 && YY.toInt() in 0..99)
            Result.Success(
                IdData(
                    gender = getGender(G),
                    birthPlace = getBirthPlace(SS) ?: Place.ALL.first(),
                    day = getDay(DD),
                    month = getMonth(MM),
                    year = getYear(X, YY),
                    idNumber = id
                )
            )
        else Result.Error("Invalid ID")
    }
}

sealed class Result {
    data class Success(val idData: IdData) : Result()
    data class Error(val message: String) : Result()
}

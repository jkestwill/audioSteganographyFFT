package common

import java.lang.Exception


fun String.toBinary(): String {
    val stringBuilder = StringBuilder()
    for (i in this) {
        stringBuilder.append(addZeros(Integer.toBinaryString(i.code), 8))
    }
    return stringBuilder.toString()
}

fun String.BinaryToString(): String {
    val stringBuilder = StringBuilder()
    var start = 0
    var end = 9
    while (end <=this.length) {
        try {
            val sas = this.substring(start, end)
            stringBuilder.append(Char(Integer.parseInt(sas, 2)))
            start += 9
            end += 9
        } catch (e: Exception) {
            println(e)
        }
    }
    return stringBuilder.toString()
}

private fun addZeros(string: String, actualLength: Int): String {
    val stringBuilder = StringBuilder(string)

    for (i in string.length..actualLength) {
        stringBuilder.insert(0, '0')

    }
    return stringBuilder.toString()
}
package com.dotypos.lib.migration.extension

import java.lang.IllegalArgumentException

fun String.toColorInt(): Int {
    if (this[0] != '#') {
        throw NumberFormatException("Unknown color")
    }

    val parsed = substring(1).toLong(16)
    return when (length) {
        7 -> (parsed or 0x00000000ff000000L).toInt()
        9 -> parsed.toInt()
        else -> throw NumberFormatException("Unknown color")
    }
}
package com.dotypos.lib.migration.extension

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Converts hex color to integer representation
 */
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

/**
 * Creates SHA-1 HEX of string
 */
fun String.sha1hex() = hash("SHA-1")?.toHex()

private fun String.hash(algorithm: String) = toByteArray(Charsets.UTF_8).hash(algorithm)

private fun ByteArray.hash(algorithm: String): ByteArray? =
    try {
        val digest = MessageDigest.getInstance(algorithm)
        digest.reset()
        digest.digest(this)
    } catch (e: NoSuchAlgorithmException) {
        null
    }

private fun ByteArray.toHex() =
    joinToString(separator = "", transform = { "%02x".format(it) })
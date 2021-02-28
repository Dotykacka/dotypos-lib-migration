package com.dotypos.lib.migration.demo.creator

import com.dotypos.lib.migration.demo.utils.IndefiniteList
import com.dotypos.lib.migration.util.KeystoreUtil
import java.io.File
import java.math.BigDecimal
import java.security.KeyStore
import kotlin.random.Random

// Enumerates
val colorNames by getNames("colorNames")
val fruits by getNames("fruits")
val herbs by getNames("herbs")
val logos by lazy(::loadLogos)
val eetKeystores by lazy(::loadKeystores)

val vatRates = listOf("10", "15", "21").map(::BigDecimal).asIndefinite()
fun getColor(random: Random) = "#AABBCC"

private fun getNames(name: String): Lazy<List<String>> = lazy {
    loadTextResource("/names/$name.txt")
        .asIndefinite()
}

private fun loadTextResource(path: String): List<String> {
    return {}.javaClass
        .getResource(path)
        .openStream()
        .bufferedReader()
        .useLines { lines -> lines.toList() }
}

private fun loadLogos(): List<ByteArray> {
    return File({}.javaClass.getResource("/logo").file)
        .walk()
        .filter { it.isFile }
        .map { file -> file.readBytes() }
        .toList()
}

private fun loadKeystores(): Map<String, KeyStore> {
    return File({}.javaClass.getResource("/eet").file)
        .walk()
        .filter { it.isFile }
        .map { file -> file.nameWithoutExtension to loadKeystore(file) }
        .toMap()
}

private fun loadKeystore(file: File): KeyStore {
    return file.inputStream().use {
        KeystoreUtil.loadPkcs12KeystoreFromInputStream(it, "eet")
    }
}

private fun <T> indefiniteListOf(vararg values: T) = listOf(*values).asIndefinite()

private fun <T> List<T>.asIndefinite() = IndefiniteList(this)
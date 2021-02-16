package com.dotypos.lib.migration.demo.creator

import com.dotypos.lib.migration.demo.utils.IndefiniteList
import java.math.BigDecimal
import kotlin.random.Random

// Enumerates
val colorNames by getNames("colorNames")
val fruits by getNames("fruits")
val herbs by getNames("herbs")

val vatRates = listOf("5", "19", "21").map(::BigDecimal).asIndefinite()
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

private fun <T> indefiniteListOf(vararg values: T) = listOf(*values).asIndefinite()

private fun <T> List<T>.asIndefinite() = IndefiniteList(this)
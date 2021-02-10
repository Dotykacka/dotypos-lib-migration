package com.dotypos.lib.migration.demo

import com.dotypos.lib.migration.demo.creator.PosDataCreator
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

//TODO: Work in progress
info { "Initializing..." }

val json = Json { prettyPrint = true }

DemoExportType.values().forEach { type ->
    info { "Creating ${"${type.id} export".bold()}..." }
    val creator = type.creator
    if (creator is PosDataCreator) {
        progress("POS data") {
            creator.createPosData()
                .let(json::encodeToString)
                .also(System.out::println)
        }
    }

}

fun progress(name: String, block: () -> Unit) {
    System.out.print("$name...")
    block()
    System.out.println("DONE")
}

fun info(message: () -> String) {
    System.out.println(message())
}

fun debug(message: () -> String) {
    System.out.println(message())
}

fun String.bold() = "\u001b[1m$this\u001B[0m"
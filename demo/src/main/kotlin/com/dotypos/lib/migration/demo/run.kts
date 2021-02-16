package com.dotypos.lib.migration.demo

import com.dotypos.lib.migration.demo.creator.PosDataCreator
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.valiktor.ConstraintViolationException
import java.io.File

//TODO: Work in progress
//TODO: Add script parameter handling for outputDir
val outputDir = "output/"

info { "Initializing..." }

val json = Json { prettyPrint = true }

DemoExportType.values().forEach { type ->
    info { "Creating ${"${type.id} export".bold()}..." }
    val creator = type.creator
    if (creator is PosDataCreator) {
        progress("POS data") {
            try {
                val json = creator.createPosData().let(json::encodeToString)
                val file = File("$outputDir${type.id}.json")
                file.createNewFile()
                file.writeText(json)
            } catch (e: ConstraintViolationException) {
                System.err.println(e.constraintViolations.toString())
            }
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
package com.dotypos.lib.migration.demo

import com.dotypos.lib.migration.demo.creator.CloudDataCreator
import com.dotypos.lib.migration.demo.creator.PosDataCreator
import com.dotypos.lib.migration.util.MigrationSerializationUtil
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.valiktor.ConstraintViolationException
import java.io.File

//TODO: Work in progress
//TODO: Add script parameter handling for outputDir
val outputDir = "output/"

info { "Initializing..." }

val prettyJson = Json { prettyPrint = true }

DemoExportType.values().forEach { type ->
    info { "Creating ${"${type.id} export".bold()}..." }
    val creator = type.creator
    if (creator is PosDataCreator) {
        progress("POS data (${type.name})") {
            try {
                writeJson(creator.createPosData(), "$outputDir${type.id}-pos.pretty.json", prettyJson)
                writeJson(creator.createPosData(), "$outputDir${type.id}-pos.json", MigrationSerializationUtil.serializer)
            } catch (e: ConstraintViolationException) {
                System.err.println(e.constraintViolations.toString())
            }
        }
    }

    if (creator is CloudDataCreator) {
        progress("Cloud data (${type.name})") {
            try {
                writeJson(creator.createCloudData(), "$outputDir${type.id}-cloud.pretty.json", prettyJson)
                writeJson(creator.createCloudData(), "$outputDir${type.id}-cloud.json", MigrationSerializationUtil.serializer)
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

inline fun <reified T> writeJson(dataObject: T, path: String, json: Json) {
    File(path).run {
        createNewFile()
        json
            .encodeToString(dataObject)
            .also(::writeText)
    }
}

fun String.bold() = "\u001b[1m$this\u001B[0m"
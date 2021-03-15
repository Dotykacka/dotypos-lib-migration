package com.dotypos.lib.migration.demo

import com.dotypos.lib.migration.demo.creator.CloudDataCreator
import com.dotypos.lib.migration.demo.creator.PosDataCreator
import com.dotypos.lib.migration.util.MigrationSerializationUtil
import heapInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.valiktor.ConstraintViolationException
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

//TODO: Work in progress
//TODO: Add script parameter handling for outputDir
val outputDir = "output/"
val jsonOutPutDir = "${outputDir}json/"
val rawDir = "${jsonOutPutDir}raw/"
val prettyDir = "${jsonOutPutDir}pretty/"
val dirsToArchive = setOf(rawDir, prettyDir)
val upload = true

info { "Initializing..." }

// Cleanup
info { "Cleaning up output dirs" }
dirsToArchive.forEach { path ->
    File(path).deleteRecursively()
    File(path).mkdirs()
}

val prettyJson = Json { prettyPrint = true }

DemoExportType.values().forEach { type ->
    info { "Creating ${"${type.id} export".bold()}..." }
    System.gc()
    info { "Heap info: $heapInfo" }
    val creator = type.makeCreator()
    if (creator is PosDataCreator) {
        progress("POS data (${type.name})") {
            try {
                writeJson(creator.createPosData(), "${prettyDir}${type.id}-pos.json", prettyJson)
                writeJson(
                    creator.createPosData(),
                    "$rawDir${type.id}-pos.json",
                    MigrationSerializationUtil.serializer
                )
            } catch (e: ConstraintViolationException) {
                System.err.println(e.constraintViolations.toString())
            }
        }
    }

    if (creator is CloudDataCreator) {
        progress("Cloud data (${type.name})") {
            try {
                val cloudData = creator.createCloudData()
                writeJson(cloudData, "$prettyDir${type.id}-cloud.json", prettyJson)
                writeJson(
                    cloudData,
                    "$rawDir${type.id}-cloud.json",
                    MigrationSerializationUtil.serializer
                )
            } catch (e: ConstraintViolationException) {
                System.err.println(e.constraintViolations.toString())
            }
        }
    }
}

// Create archive
createArchive()


if (upload) {
    info { "Uploading migration file" }
    val devicePath = "/sdcard/Download/"
    "adb push ${rawDir}restaurant-small-pos.json ${devicePath}migration.json".runCommand()
    System.exit(0)
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

fun error(message: () -> String) {
    System.out.println(message())
}

fun String.runCommand(workingDir: File? = null) {
    ProcessBuilder(*split(" ").toTypedArray())
        .also {
            if (workingDir != null) {
                it.directory(workingDir)
            }
        }
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor(60, TimeUnit.MINUTES)
}

inline fun <reified T> writeJson(dataObject: T, path: String, json: Json) {
    File(path).run {
        createNewFile()
        json
            .encodeToString(dataObject)
            .also(::writeText)
    }
}

fun createArchive() {
    info { "Creating ZIP archive" }
    val rootPath = FileSystems.getDefault().getPath(jsonOutPutDir)
    val outputPath = FileSystems.getDefault().getPath("${outputDir}mock.zip")
    try {
        ZipOutputStream(Files.newOutputStream(outputPath)).use { outputStream ->
            dirsToArchive
                .map(::File)
                .flatMap { it.walk() }
                .filter { it.isFile }
                .forEach { file ->
                    val filePath = Paths.get(file.path)
                    val entry = ZipEntry(rootPath.relativize(filePath).toString())
                    outputStream.putNextEntry(entry)
                    Files.copy(filePath, outputStream)
                    outputStream.closeEntry()
                }
        }

    } catch (e: Exception) {
        e.printStackTrace()
        error { "Can't create ZIP file" }
    }


    /*val outputStream = File("${outputDir}mock.zip")
        .let(::FileOutputStream)
        .let(::ZipOutputStream)

    dirsToArchive
        .map(::File)
        .flatMap { it.walk() }
        .filter { it.isFile }
        .forEach { file ->
            val parent
            val entry = ZipEntry(file.name)
        }*/
}

fun String.bold() = "\u001b[1m$this\u001B[0m"
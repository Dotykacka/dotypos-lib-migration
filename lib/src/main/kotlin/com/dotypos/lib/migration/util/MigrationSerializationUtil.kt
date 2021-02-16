package com.dotypos.lib.migration.util

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MigrationSerializationUtil {

    val serializer = Json
    val deserializer = Json

    inline fun <reified T> serializeObject(data: T) = serializer.encodeToString(data)

    inline fun <reified T> parseData(data: String) = deserializer.decodeFromString<T>(data)
}
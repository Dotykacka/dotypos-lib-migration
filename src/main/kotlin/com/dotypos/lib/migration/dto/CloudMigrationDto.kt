package com.dotypos.lib.migration.dto

import kotlinx.serialization.SerialName

data class CloudMigrationDto(
    /**
     * Data of migration result returned by Dotykačka POS
     */
    @SerialName("migrationResultData")
    val migrationResultData: String
)
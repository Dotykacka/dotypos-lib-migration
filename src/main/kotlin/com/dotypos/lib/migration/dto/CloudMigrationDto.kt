package com.dotypos.lib.migration.dto

import com.dotypos.lib.migration.dto.entity.DocumentMigrationDto
import com.dotypos.lib.migration.dto.entity.MoneyOperationMigrationDto
import com.dotypos.lib.migration.dto.entity.StockOperationMigrationDto
import kotlinx.serialization.SerialName

data class CloudMigrationDto(
    /**
     * Data of migration result returned by Dotykačka POS
     */
    @SerialName("migrationResultData")
    val migrationResultData: String,

    /**
     * Issued documents (+items)
     */
    @SerialName("documents")
    val documents: List<DocumentMigrationDto>,

    /**
     * List of all money operations (including payments of issued documents)
     */
    @SerialName("moneyOperations")
    val moneyOperations: List<MoneyOperationMigrationDto>,

    /**
     * List of all stock operations
     */
    @SerialName("stockOperations")
    val stockOperations: List<StockOperationMigrationDto>,
)
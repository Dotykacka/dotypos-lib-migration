package com.dotypos.lib.migration.dto

import com.dotypos.lib.migration.dto.entity.DocumentMigrationDto
import com.dotypos.lib.migration.dto.entity.MoneyOperationMigrationDto
import com.dotypos.lib.migration.dto.entity.StockOperationMigrationDto
import com.dotypos.lib.migration.dto.entity.StockTransactionMigrationDto
import com.dotypos.lib.migration.dto.validation.hasUniqueItemIds
import com.dotypos.lib.migration.dto.validation.validateRelationsTo
import com.dotypos.validator.validation.isNotBlank
import com.dotypos.validator.validationOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
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
     * List of stock transactions which contains grouping metadata of [stock operations][stockOperations].
     */
    @SerialName("stockTransactions")
    val stockTransactions: List<StockTransactionMigrationDto>,

    /**
     * List of all stock operations
     */
    @SerialName("stockOperations")
    val stockOperations: List<StockOperationMigrationDto>,
) {
    init {
        validationOf(CloudMigrationDto::migrationResultData) {
            isNotBlank()
        }

        validationOf(CloudMigrationDto::documents) {
            hasUniqueItemIds()
        }

        validationOf(CloudMigrationDto::moneyOperations) {
            hasUniqueItemIds()
            validateRelationsTo(
                key = MoneyOperationMigrationDto::documentId,
                entities = CloudMigrationDto::documents,
            )
        }

        validationOf(CloudMigrationDto::stockTransactions) {
            hasUniqueItemIds()
        }

        validationOf(CloudMigrationDto::stockOperations) {
            hasUniqueItemIds()
        }

    }
}
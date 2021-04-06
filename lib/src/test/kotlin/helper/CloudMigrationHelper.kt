package helper

import com.dotypos.lib.migration.dto.CloudMigrationDto

object CloudMigrationHelper {

    fun createEmptyCloudMigration() = CloudMigrationDto(
        migrationResultData = "1/123/456",
        documents = emptyList(),
        moneyOperations = emptyList(),
        stockTransactions = emptyList(),
        stockOperations = emptyList(),
    )
}
package com.dotypos.lib.migration.demo.creator

import com.dotypos.lib.migration.dto.CloudMigrationDto
import com.dotypos.lib.migration.dto.PosMigrationDto

interface CloudDataCreator: DataCreator {
    fun createCloudData(): CloudMigrationDto
}
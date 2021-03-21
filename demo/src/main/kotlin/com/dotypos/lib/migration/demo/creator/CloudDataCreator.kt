package com.dotypos.lib.migration.demo.creator

import com.dotypos.lib.migration.dto.CloudMigrationDto

interface CloudDataCreator: DataCreator {
    fun createCloudData(): CloudMigrationDto
}
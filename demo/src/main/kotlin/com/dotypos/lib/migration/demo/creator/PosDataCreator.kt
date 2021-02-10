package com.dotypos.lib.migration.demo.creator

import com.dotypos.lib.migration.dto.PosMigrationDto

interface PosDataCreator: DataCreator {
    fun createPosData(): PosMigrationDto
}
package com.dotypos.lib.migration.dto.entity.iface

import com.dotypos.lib.migration.dto.enumerate.MigrationMeasurementUnit

interface WithMeasurementUnit {

    /**
     * Measurement unit of entity
     */
    val measurementUnit: MigrationMeasurementUnit

    companion object {
        const val SERIAL_NAME = "measurementUnit"
    }
}
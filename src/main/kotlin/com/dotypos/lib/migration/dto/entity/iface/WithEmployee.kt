package com.dotypos.lib.migration.dto.entity.iface

import kotlinx.serialization.SerialName

interface WithEmployee {

    /**
     * ID of related [employee][EmployeeMigrationDto]
     */
    @SerialName(SERIAL_NAME)
    val employeeId: Long

    companion object {
        const val SERIAL_NAME = "employeeId"
    }
}
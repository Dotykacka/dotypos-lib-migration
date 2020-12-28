package com.dotypos.lib.migration.dto.enumerate.permission

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EmployeeStockPermission {
    /**
     * Enables access to the Stock Management App (also [EmployeePosPermission.STOCK_MANAGEMENT] required to be set for user).
     */
    @SerialName("stockView")
    STOCK_VIEW,

    // TODO: All permissions
}
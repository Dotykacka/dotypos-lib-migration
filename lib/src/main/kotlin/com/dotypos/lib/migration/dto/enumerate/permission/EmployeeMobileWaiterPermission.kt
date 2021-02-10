package com.dotypos.lib.migration.dto.enumerate.permission

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EmployeeMobileWaiterPermission {
    /**
     * Allows to issue order in Mobile Waiter App.
     */
    @SerialName("orderIssue")
    ORDER_ISSUE
}
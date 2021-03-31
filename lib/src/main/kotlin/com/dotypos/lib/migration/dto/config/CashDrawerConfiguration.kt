package com.dotypos.lib.migration.dto.config

import com.dotypos.lib.migration.dto.enumerate.permission.EmployeePosPermission
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CashDrawerConfiguration(
    /**
     * Opens cash drawer after print of receipt.
     */
    @SerialName("openAfterReceiptPrint")
    val openAfterReceiptPrint: Boolean = true,

    /**
     * Opens cash drawer after payment confirmation
     */
    @SerialName("openAfterPayment")
    val openAfterPayment: Boolean = true,

    /**
     * If both [openAfterReceiptPrint] and [openAfterPayment] selected, cash drawer is opened only once.
     */
    @SerialName("openOnlyOnce")
    val openOnlyOnce: Boolean = true,

    /**
     * Changes behavior of payment dialog -> No payment method is selected on dialog opening.
     * Selection of payment method also triggers cash drawer opening.
     */
    @SerialName("openAfterPaymentMethodSelection")
    val openAfterPaymentMethodSelection: Boolean = false,

    /**
     * Displays manual button to open cash drawer. [EmployeePosPermission.CASH_DRAWER_OPEN] is required.
     */
    @SerialName("showManualButton")
    val showManualButton: Boolean = false,
)
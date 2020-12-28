package com.dotypos.lib.migration.dto.enumerate.permission

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EmployeePosPermission {
    /**
     * Enables access to full sales overview
     */
    @SerialName("salesViewAll")
    SALES_VIEW_ALL,

    /**
     * Enables sales overview for current shift
     */
    @SerialName("salesViewCurrentShift")
    SALES_VIEW_CURRENT_SHIFT,

    /**
     * Allows to display expected state of finance in register closure dialog
     */
    @SerialName("cashBalance")
    CASH_BALANCE,

    /**
     * Allows to print shift report automatically. Is required to have enabled "Automatically print ZReport" in the Print Settings.
     */
    @SerialName("printZReports")
    PRINT_ZREPORTS,

    /**
     * Allows to display profit on the Sales Overview and on the shift report.
     */
    @SerialName("showProfit")
    SHOW_PROFIT,

    /**
     * Allows access to Product Management
     */
    @SerialName("productEdit")
    PRODUCT_EDIT,

    /**
     * Allows access to User Management
     */
    @SerialName("userEdit")
    USER_EDIT,

    /**
     * Enables to display button Customer for possibility to choose customer manually.
     */
    @SerialName("customerSelect")
    CUSTOMER_SELECT,

    /**
     * Enables to create or edit customer.
     */
    @SerialName("customerEdit")
    CUSTOMER_EDIT,

    /**
     * TODO
     */
    @SerialName("cashOut")
    CASH_OUT,

    /**
     * TODO
     */
    @SerialName("orderHistoryDisplay")
    ORDER_HISTORY_DISPLAY,

    /**
     * TODO
     */
    @SerialName("cancelDocument")
    CANCEL_DOCUMENT,

    /**
     * TODO
     */
    @SerialName("cancelOpenOrder")
    CANCEL_OPEN_ORDER,

    /**
     * TODO
     */
    @SerialName("changeParkedItem")
    CHANGE_PARKED_ITEM,

    /**
     * TODO
     */
    @SerialName("changeNotParkedItem")
    CHANGE_NOT_PARKED_ITEM,

    /**
     * TODO
     */
    @SerialName("controlReceiptPrint")
    CONTROL_RECEIPT_PRINT,

    /**
     * TODO
     */
    @SerialName("controlReceiptPrint")
    DISCOUNT_OFFER,

    /**
     * TODO
     */
    @SerialName("issueNotPaid")
    ISSUE_NOT_PAID,

    /**
     * TODO
     */
    @SerialName("writeOff")
    WRITE_OFF,

    /**
     * TODO
     */
    @SerialName("cashDrawerOpen")
    CASH_DRAWER_OPEN,

    /**
     * TODO
     */
    @SerialName("stockManagement")
    STOCK_MANAGEMENT
}
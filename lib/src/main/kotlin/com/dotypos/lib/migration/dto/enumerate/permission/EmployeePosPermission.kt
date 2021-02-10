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
     * Enables access to the In/Out cash for possibility to register cash operations.
     */
    @SerialName("cashOut")
    CASH_OUT,

    /**
     * Enables access to the History of issued receipts.
     */
    @SerialName("orderHistoryDisplay")
    ORDER_HISTORY_DISPLAY,

    /**
     * Enables to make cancellations of the receipts in the History.
     */
    @SerialName("cancelDocument")
    CANCEL_DOCUMENT,

    /**
     * Enables to make cancellations of the open orders.
     */
    @SerialName("cancelOpenOrder")
    CANCEL_OPEN_ORDER,

    /**
     * Enables to edit parked items on the opened orders. Enables full edit, including to add or remove pieces, or to delete item from the order.
     */
    @SerialName("changeParkedItem")
    CHANGE_PARKED_ITEM,

    /**
     * Allows editing the quantity and price of a newly added, not parked item of an open order. It also allows you to remove non-parked items from the order.
     */
    @SerialName("changeNotParkedItem")
    CHANGE_NOT_PARKED_ITEM,

    /**
     * Enables to print the Review Receipt from the open order.
     */
    @SerialName("controlReceiptPrint")
    CONTROL_RECEIPT_PRINT,

    /**
     * Enables to give discounts on the open order / item of the open order.
     */
    @SerialName("controlReceiptPrint")
    DISCOUNT_OFFER,

    /**
     * Enables to issue the receipt without payment.
     */
    @SerialName("issueNotPaid")
    ISSUE_NOT_PAID,

    /**
     * Enables to use payment method the Write-Offs. Is required to have enabled "Write-Offs" in the Payment Settings
     */
    @SerialName("writeOff")
    WRITE_OFF,

    /**
     * Enables to display button "Open Cash drawer". Is required to have enabled "Display open button on main screen" in the Cash drawer settings.
     */
    @SerialName("cashDrawerOpen")
    CASH_DRAWER_OPEN,

    /**
     * Enables access to the Stock app. Permission is superior to other Stock permissions.
     */
    @SerialName("stockManagement")
    STOCK_MANAGEMENT
}
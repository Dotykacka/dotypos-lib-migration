package com.dotypos.lib.migration.dto.enumerate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PrintTaskType {
    /**
     * Print receipts (default on primary printer)
     */
    @SerialName("receipt")
    RECEIPT,

    /**
     * Print kitchen orders
     */
    @SerialName("kitchenOrder")
    KITCHEN_ORDER,

    /**
     * Print reports (default on primary printer)
     */
    @SerialName("report")
    REPORT,

    /**
     * Print invoices
     */
    @SerialName("invoice")
    INVOICE,

    /**
     * Print cash in/cash out documents (default on primary printer)
     */
    @SerialName("cashInOut")
    CASH_IN_OUT,

    /**
     * Print price tags (default on primary printer)
     */
    @SerialName("priceTag")
    PRICE_TAG
}
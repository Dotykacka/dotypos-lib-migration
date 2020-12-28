package com.dotypos.lib.migration.dto.enumerate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ProductStockOverdraftBehavior {
    /**
     * Allow to sale item when on stock quantity drops bellow 0.
     */
    @SerialName("allow")
    ALLOW,

    /**
     * Displays a warning when an out-of-stock item should be sold.
     */
    @SerialName("warning")
    WARNING,

    /**
     * Prohibits sales if there are not enough items in stock.
     */
    @SerialName("deny")
    DENY
}
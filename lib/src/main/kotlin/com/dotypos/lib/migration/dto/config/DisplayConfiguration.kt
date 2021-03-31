package com.dotypos.lib.migration.dto.config

import com.dotypos.validator.validation.isBetweenOrNull
import com.dotypos.validator.validationOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DisplayConfiguration(
    /**
     * Default screen to be shown on app opening/after document issue.
     */
    @SerialName("defaultScreen")
    val defaultScreen: DefaultScreen = DefaultScreen.SALE,

    /**
     * Sale screen configuration
     */
    @SerialName("saleScreen")
    val saleScreen: SaleScreenConfiguration = SaleScreenConfiguration(),
) {

    @Serializable
    data class SaleScreenConfiguration(
        /**
         * Number of columns to be displayed on sale screen. Valid values: null, 2-6.
         * null = automatic according the display size
         */
        @SerialName("columns")
        val columns: Int? = null,

        /**
         * Size of boxes font on sale screens.
         */
        @SerialName("fontSize")
        val fontSize: FontSize = FontSize.MEDIUM,

        /**
         * Display price of products on sale screen.
         */
        @SerialName("displayPrice")
        val displayPrice: Boolean = true,

        /**
         * Display points to be gained if presented on product.
         */
        @SerialName("displayPoints")
        val displayPoints: Boolean = false,

        /**
         * Sets whether hidden products will be included in search.
         */
        @SerialName("includeHiddenProductsInSearch")
        val includeHiddenProductsInSearch: Boolean = false,

        /**
         * Displays PLU button in top left corner on sale screen.
         */
        @SerialName("displayPluButton")
        val displayPluButton: Boolean = false,
    ) {
        init {
            validationOf(SaleScreenConfiguration::columns)
                .isBetweenOrNull(2, 6)
        }

        @Serializable
        enum class FontSize {
            @SerialName("small")
            SMALL,

            @SerialName("medium")
            MEDIUM,

            @SerialName("large")
            LARGE,
        }
    }

    @Serializable
    enum class DefaultScreen {
        /**
         * Sale screen according the [PosConfigurationDto.mode]
         */
        @SerialName("sale")
        SALE,

        /**
         * List of open orders
         */
        @SerialName("openOrders")
        OPEN_ORDERS,

        /**
         * Table map
         */
        @SerialName("tableMap")
        TABLE_MAP,
    }
}
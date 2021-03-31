package com.dotypos.lib.migration.dto.config

import com.dotypos.lib.migration.dto.config.PosConfigurationDto.DocumentNumberingConfiguration.Companion.NUMBERING_FORMAT_REGEX
import com.dotypos.lib.migration.dto.entity.iface.WithCountry
import com.dotypos.lib.migration.dto.entity.iface.WithName
import com.dotypos.lib.migration.dto.enumerate.PaymentMethod
import com.dotypos.validator.validation.matches
import com.dotypos.validator.validation.matchesOrNull
import com.dotypos.validator.validationOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PosConfigurationDto(

    @SerialName("name")
    override val name: String,

    /**
     * Operation mode of cash register - affects the layout of main sale screen
     */
    @SerialName("mode")
    val mode: Mode,

    /**
     * Country of POS system in [ISO 3166 alpha2](https://www.iso.org/obp/ui/#search/code/) format,
     * behavior changes are applied for different countries
     */
    @SerialName(WithCountry.SERIALIZED_NAME)
    override val country: String = WithCountry.DEFAULT_COUNTRY,

    /**
     * Default currency of POS in [ISO 4217 format](https://www.iso.org/iso-4217-currency-codes.html)
     */
    @SerialName("currency")
    val currency: String,

    /**
     * Alternaive currency configuration, null = disable alternative currency
     */
    @SerialName("alternativeCurrency")
    val alternativeCurrency: AlternativeCurrencyConfiguration? = null,

    /**
     * Configuration of rounding behavior
     */
    @SerialName("rounding")
    val rounding: RoundingConfiguration,

    /**
     * Allow negative prices to be entered in custom input
     */
    @SerialName("negativePrices")
    val negativePrices: Boolean,

    /**
     * Allow negative amount to be set for items on bill
     */
    @SerialName("negativeAmount")
    val negativeAmount: Boolean,

    /**
     * Increases quantity of item on the bill instead of creating new one
     */
    @SerialName("groupItems")
    val groupItems: Boolean,

    /**
     * It does not show PaymentDialog by default but issues receipt paid with cash
     */
    @SerialName("quickPayMode")
    val quickPayMode: Boolean,

    /**
     * Allowed payment methods
     */
    @SerialName("paymentMethods")
    val paymentMethods: Set<PaymentMethod>,

    /**
     * Disables issuing the document when any of the items will be sold with the price below the purchase price.
     */
    @SerialName("disableSaleBellowPurchasePrice")
    val disableSaleBellowPurchasePrice: Boolean,

    /**
     * Shows QR code scanner buttons in UI.
     */
    @SerialName("useQrCodeScanner")
    val useQrCodeScanner: Boolean,

    /**
     * If the customer with an email presented on the receipt,
     * then the receipt won't be printed but sent to the customer's email instead.
     */
    @SerialName("sendReceiptsByEmail")
    val sendReceiptsByEmail: Boolean,

    /**
     * Configuration of document numbering format
     */
    @SerialName("documentNumbering")
    val documentNumbering: DocumentNumberingConfiguration,

    /**
     * Prefix which will be printed before order number. It is used to distinguish orders.
     */
    @SerialName("orderNumberPrefix")
    val orderNumberPrefix: String = "",

    /**
     * List of FiscalizationConfigurations (each for every seller)
     */
    @SerialName("czFiscalizationConfigurations")
    val czFiscalizationConfigurations: List<CzFiscalizationConfiguration>,

    /**
     * Configuration of display features
     */
    @SerialName("display")
    val display: DisplayConfiguration = DisplayConfiguration(),

    /**
     * Configuration of cash drawer behavior
     */
    @SerialName("cashDrawer")
    val cashDrawer: CashDrawerConfiguration = CashDrawerConfiguration(),

    /**
     * ID of default [warehouse][WarehouseMigrationDto]
     */
    @SerialName("defaultWarehouseId")
    val defaultWarehouseId: Long,
) : WithName, WithCountry {

    @Serializable
    enum class Mode {
        /**
         * Catalog of products grouped by categories: selection of categories is place at the top of the layout
         */
        @SerialName("catalogDefault")
        CATALOG_DEFAULT,

        /**
         * Catalog of products grouped by categories: selection of categories is place at the top of the layout
         */
        @SerialName("catalogMasterDetail")
        CATALOG_SIMPLIFIED,

        /**
         * PLU/EAN keyboard
         */
        @SerialName("instantSale")
        INSTANT_SALE,
    }

    @Serializable
    data class RoundingConfiguration(
        /**
         * Number of decimal digits shown in UI
         */
        @SerialName("displayed")
        val displayed: Int,

        /**
         * Number of decimal digits used for rounding of items
         */
        @SerialName("items")
        val items: Int,

        /**
         * Number of decimal digits used for rounding of bill
         */
        @SerialName("total")
        val total: Int
    )

    @Serializable
    data class DocumentNumberingConfiguration(
        /**
         * Numbering format of receipts, required format, matching [NUMBERING_FORMAT_REGEX]:
         * PREFIX@COUNTER_DIGITS
         * where PREFIX can include date variables. If prefix part is changed the document counter is reset to 0 zero
         * Examples:
         * %Y-%m-%d-@4 -> 2021-04-15-0001
         * @6 -> 000023
         */
        @SerialName("receiptFormat")
        val receiptFormat: String,

        /**
         * Formatted number of last receipt
         */
        @SerialName("receiptLastNumber")
        val receiptLastNumber: String,

        /**
         * Numbering format of invoices, [receiptFormat] is used if `null`
         */
        @SerialName("invoiceFormat")
        val invoiceFormat: String?,

        /**
         * Formatted number of last invoice - required if [invoiceFormat] is not `null`
         */
        @SerialName("invoiceLastNumber")
        val invoiceLastNumber: String?,

        /**
         * Numbering format of cancellation and corrective invoices, [invoiceFormat] is used if `null`
         */
        @SerialName("cancellationInvoiceFormat")
        val cancellationInvoiceFormat: String?,

        /**
         * Formatted number of last cancellation invoice - required if [cancellationInvoiceFormat] is not `null`
         */
        @SerialName("cancellationInvoiceLastNumber")
        val cancellationInvoiceLastNumber: String?,
    ) {
        init {
            validationOf(DocumentNumberingConfiguration::receiptFormat)
                .matches(NUMBERING_FORMAT_REGEX)
            validationOf(DocumentNumberingConfiguration::receiptLastNumber)
                .matches(createNumberingFormatValidationRegex(receiptFormat))
            if (invoiceFormat != null) {
                validationOf(DocumentNumberingConfiguration::invoiceFormat)
                    .matchesOrNull(NUMBERING_FORMAT_REGEX)
                validationOf(DocumentNumberingConfiguration::invoiceLastNumber).matchesOrNull(
                    createNumberingFormatValidationRegex(invoiceFormat)
                )
            }
            if (cancellationInvoiceFormat != null) {
                validationOf(DocumentNumberingConfiguration::cancellationInvoiceFormat)
                    .matchesOrNull(NUMBERING_FORMAT_REGEX)
                validationOf(DocumentNumberingConfiguration::cancellationInvoiceLastNumber).matchesOrNull(
                    createNumberingFormatValidationRegex(cancellationInvoiceFormat)
                )
            }
        }

        companion object {
            val NUMBERING_FORMAT_REGEX = Regex("(([0-9a-zA-Z.,:;/#\\-_ ]|%[dmyY])*)@([0-9]+)")

            private fun createNumberingFormatValidationRegex(format: String): Regex {
                val (prefix, counterDigits) = format.split("@")
                val regexPrefix = prefix
                    .replace("%d", "([012][1-9]|[3][01])")
                    .replace("%m", "([0][1-9]|[1][012])")
                    .replace("%y", "([0-9]{2})")
                    .replace("%Y", "([0-9]{4})")
                return Regex("$regexPrefix[0-9]{$counterDigits}")
            }
        }
    }
}
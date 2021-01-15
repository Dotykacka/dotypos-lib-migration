package com.dotypos.lib.migration.dto.config

import com.dotypos.lib.migration.dto.entity.iface.WithCountry
import com.dotypos.lib.migration.dto.entity.iface.WithName
import com.dotypos.lib.migration.dto.enumerate.PaymentMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PosConfigurationDto(

    @SerialName("name")
    override val name: String,

    /**
     * Country of POS system in [ISO 3166 alpha2](https://www.iso.org/obp/ui/#search/code/) format,
     * behavior changes are applied for different countries
     */
    @SerialName(WithCountry.DEFAULT_COUNTRY)
    override val country: String,

    /**
     * Default currency of POS in [ISO 4217 format](https://www.iso.org/iso-4217-currency-codes.html)
     */
    @SerialName("currency")
    val currency: String,

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
     * List of FiscalizationConfigurations (each for every seller)
     */
    @SerialName("czFiscalizationConfigurations")
    val czFiscalizationConfigurations: List<CzFiscalizationConfiguration>,

    /**
     * ID of default [warehouse][WarehouseMigrationDto]
     */
    @SerialName("defaultWarehouseId")
    val defaultWarehouseId: Long,
) : WithName, WithCountry {

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
         * Numbering format of receipts
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
    )
}
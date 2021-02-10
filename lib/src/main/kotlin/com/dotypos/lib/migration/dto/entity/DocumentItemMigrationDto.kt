@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.*
import com.dotypos.lib.migration.dto.enumerate.MigrationMeasurementUnit
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

/**
 * Item of issued document - basically snapshot of [product][ProductMigrationDto] with additional properties
 */
@Serializable
data class DocumentItemMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * ID of source [product][ProductMigrationDto].
     * Use [SIMPLE_SALE_PRODUCT_ID] if created as [simple sale product][Type.SIMPLE_SALE]
     */
    @SerialName("productId")
    val productId: Long,

    /**
     * ID of parent [category][CategoryMigrationDto].
     * Use [SIMPLE_SALE_CATEGORY_ID] if created as [simple sale product][Type.SIMPLE_SALE]
     */
    @SerialName("categoryId")
    val categoryId: Long,

    /**
     * ID of related [document item][DocumentItemMigrationDto] - used to connect item of cancellation receipt/invoice
     * to item on original receipts - required for correct partial cancellations handling.
     */
    @SerialName("relatedDocumentItemId")
    val relatedDocumentItemId: Long?,

    /**
     * ID of [course][CourseMigrationDto]
     */
    @SerialName("courseId")
    val courseId: Long?,

    /**
     * Type of document item
     */
    @SerialName("type")
    val type: Type,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    @SerialName(WithCustomPrintName.SERIAL_NAME)
    override val printName: String?,

    /**
     * Product subtitle. Printed on various documents (kitchen print, receipt, invoice,...).
     */
    @SerialName("subtitle")
    val subtitle: String,

    /**
     * Item note. It is added to order item and can be modified on order item by cashier.
     * Printed on multiple documents.
     */
    @SerialName("note")
    val note: String,

    @SerialName(WithColor.SERIALIZED_NAME)
    override val hexColor: String,

    /**
     * Product packaging affects the number of items added to the account and the modification of the item on the account using the +/- buttons.
     * It is always added and removed in steps of package size.
     */
    @SerialName("packaging")
    val packaging: BigDecimal,

    @SerialName(WithMeasurementUnit.SERIAL_NAME)
    override val measurementUnit: MigrationMeasurementUnit,

    /**
     * List of supported [EAN/UPC](https://www.gs1.org/standards/barcodes/ean-upc) codes
     */
    @SerialName("ean")
    val ean: List<String>,

    /**
     * Sold quantity (positive for sales, negative for cancellations (documentType))
     */
    @SerialName("quantity")
    val quantity: BigDecimal,

    /**
     * Calculated unit purchase price (without VAT for VAT payer), null if purchase price is not tracked
     */
    @SerialName("unitPurchasePrice")
    val unitPurchasePrice: BigDecimal?,

    /**
     * Base unit price without VAT before discounts
     */
    @SerialName("baseUnitPriceWithoutVat")
    val baseUnitPriceWithoutVat: BigDecimal,

    /**
     * Base unit price with VAT before discounts
     */
    @SerialName("baseUnitPriceWithVat")
    val baseUnitPriceWithVat: BigDecimal,

    /**
     * Actually billed unit price without VAT after discounts, calculated with `MathContext.DECIMAL128`, not rounded
     */
    @SerialName("billedUnitPriceWithoutVat")
    val billedUnitPriceWithoutVat: BigDecimal,

    /**
     * Actually billed price with VAT after discounts, calculated with `MathContext.DECIMAL128`, not rounded
     */
    @SerialName("billedUnitPriceWithVat")
    val billedUnitPriceWithVat: BigDecimal,

    /**
     * Actually billed unit price without VAT after discounts, rounded according to `PosConfiguration.rounding.items`
     * using `BigDecimal.ROUND_HALF_UP`
     */
    @SerialName("totalPriceWithoutVat")
    val totalPriceWithoutVat: BigDecimal,

    /**
     * Actually billed price with VAT after discounts, rounded according to `PosConfiguration.rounding.items`
     * using `BigDecimal.ROUND_HALF_UP`
     */
    @SerialName("totalPriceWithVat")
    val totalPriceWithVat: BigDecimal,

    /**
     * VAT rate, `null` for exempted VAT.
     */
    @SerialName("vatRate")
    val vatRate: BigDecimal?,

    /**
     * Discount of item in percents (0.01) for 1% discount
     */
    @SerialName("discountPercent")
    val discountPercent: BigDecimal,

    /**
     * Amount of points to be added to customers account
     */
    @SerialName("points")
    val points: BigDecimal,

    /**
     * The number of points for which the items was be sold or `null`.
     */
    @SerialName("priceInPoints")
    val priceInPoints: BigDecimal?,

    /**
     * Configures whether the product will be deducted from stock.
     */
    @SerialName("stockDeducted")
    val stockDeducted: Boolean,

    @SerialName(WithTags.SERIAL_NAME)
    override val tags: List<String>,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long = WithVersion.DEFAULT_VALUE,
) : BaseEntityDto(), WithName, WithCustomPrintName, WithColor, WithMeasurementUnit, WithTags {

    @Serializable
    enum class Type {
        /**
         * Common sale done by picking item from items catalogue
         */
        @SerialName("common")
        COMMON,

        /**
         * Weighted item created with certified connection
         */
        @SerialName("welmecWeight")
        WELMEC_WEIGHT,

        /**
         * Item created from weighted EAN
         */
        @SerialName("welmecEanWeight")
        WELMEC_EAN_WEIGHT,

        /**
         * Item created by Simple Sale screen (only entry of name, vat rate and price) - not connected to product/category
         */
        @SerialName("simpleSale")
        SIMPLE_SALE,

        /**
         * Rounding item
         */
        @SerialName("rounding")
        ROUNDING,
    }

    companion object {
        /**
         * Product ID to be used for simple sales
         */
        const val SIMPLE_SALE_PRODUCT_ID = 0L
        const val ROUNDING_PRODUCT_ID = 0L

        /**
         * Category ID to be used for simple sales
         */
        const val SIMPLE_SALE_CATEGORY_ID = 0L
        const val ROUNDING_CATEGORY_ID = 0L

        /**
         * Convenient function for creating rounding items
         * @param id ID of document item
         * @param name name
         * @param printName printable name (name used if `null`)
         * @param relatedDocumentItemId id of related [document item][DocumentItemMigrationDto]
         * @param roundingValue value of rounding - will be used for all price related properties
         */
        fun createRounding(
            id: Long,
            name: String,
            printName: String? = null,
            relatedDocumentItemId: Long? = null,
            roundingValue: BigDecimal,
        ) = DocumentItemMigrationDto(
            id = id,
            productId = ROUNDING_PRODUCT_ID,
            categoryId = ROUNDING_CATEGORY_ID,
            relatedDocumentItemId = relatedDocumentItemId,
            courseId = null,
            type = Type.ROUNDING,
            name = name,
            printName = printName,
            subtitle = "",
            note = "",
            hexColor = "#000000",
            packaging = BigDecimal.ONE,
            measurementUnit = MigrationMeasurementUnit.PIECE,
            ean = emptyList(),
            quantity = BigDecimal.ONE,
            unitPurchasePrice = null,
            baseUnitPriceWithoutVat = roundingValue,
            baseUnitPriceWithVat = roundingValue,
            billedUnitPriceWithoutVat = roundingValue,
            billedUnitPriceWithVat = roundingValue,
            totalPriceWithoutVat = roundingValue,
            totalPriceWithVat = roundingValue,
            vatRate = BigDecimal.ZERO,
            points = BigDecimal.ZERO,
            discountPercent = BigDecimal.ZERO,
            priceInPoints = null,
            stockDeducted = false,
            tags = emptyList(),
            version = WithVersion.DEFAULT_VALUE
        )
    }
}

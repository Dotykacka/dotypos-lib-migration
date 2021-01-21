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

@Serializable
data class StockOperationMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * ID of grouping [transaction][StockTransactionMigrationDto].
     */
    @SerialName("stockTransactionId")
    val stockTransactionId: Long?,

    /**
     * ID of [product][ProductMigrationDto]
     */
    @SerialName("productId")
    val productId: Long,

    /**
     * ID of [warehouse][WarehouseMigrationDto]
     */
    @SerialName("warehouseId")
    val warehouseId: Long,

    @SerialName(WithEmployee.SERIAL_NAME)
    override val employeeId: Long,

    @SerialName(SellerRelated.SERIAL_NAME)
    override val sellerId: Long?,

    /**
     * ID of [document][DocumentMigrationDto] to which operation is related
     * Required for [Type.SALE],[Type.WRITE_OFF]
     */
    @SerialName("documentId")
    val documentId: Long?,

    /**
     * Changed quantity of product
     */
    @SerialName("quantity")
    val quantity: BigDecimal,

    /**
     * Indicates quantity after stock operation is performed. It should show current
     * quantity at stock at this point of time - expected only for [Type.STOCK_TAKING]
     */
    @SerialName("quantityStatus")
    val quantityStatus: BigDecimal?,

    @SerialName(WithMeasurementUnit.SERIAL_NAME)
    override val measurementUnit: MigrationMeasurementUnit,

    /**
     * Actual unit purchase price for stock operation
     * `null` = don't track
     */
    @SerialName("purchasePrice")
    val purchasePrice: BigDecimal?,

    /**
     * New recalculated purchase price for product/warehouse.
     * Should be presented on [Type.PURCHASE],[Type.CORRECTION],[Type.TRANSFER] in case that,
     * purchasePrice was already tracked in the past.
     */
    @SerialName("avgPurchasePrice")
    val avgPurchasePrice: BigDecimal?,

    /**
     * Currency code [ISO 4217 format](https://www.iso.org/iso-4217-currency-codes.html)
     * Only primary currency of POS supported
     */
    @SerialName(WithCurrency.SERIALIZED_NAME)
    val currency: String,

    /**
     * Stock transaction note - used primary for purchases or write-off.
     */
    @SerialName("note")
    val note: String,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithEmployee, WithMeasurementUnit, SellerRelated {

    @Serializable
    enum class Type {
        /**
         * Sale of product (requires related documentId)
         */
        @SerialName("sale")
        SALE,

        /**
         * Purchase of product
         */
        @SerialName("purchase")
        PURCHASE,

        /**
         * Transfer between two warehouses (negative quantity for direction out/positive to direction in)
         */
        @SerialName("transfer")
        TRANSFER,

        /**
         * Correction of quantity
         */
        @SerialName("correction")
        CORRECTION,

        /**
         * Difference between delivery note claimed purchase quantity and reality
         */
        @SerialName("deliveryDiff")
        DELIVERY_DIFF,

        /**
         * Written-of product (requires related write-off documentId)
         */
        @SerialName("writeOff")
        WRITE_OFF,

        /**
         * Correction created as result of stock taking, all older stock operations won't change stock quantity
         */
        @SerialName("stockTaking")
        STOCK_TAKING,
    }
}
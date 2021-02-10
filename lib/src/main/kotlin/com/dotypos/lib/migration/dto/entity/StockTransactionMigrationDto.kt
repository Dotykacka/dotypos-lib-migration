package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithVersion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

data class StockTransactionMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * ID of [warehouse][WarehouseMigrationDto]
     */
    @SerialName("warehouseId")
    val warehouseId: Long,

    /**
     * ID of [supplier][SupplierMigrationDto]
     */
    @SerialName("supplierId")
    val supplierId: Long?,

    /**
     * Number of invoice related to transaction
     */
    @SerialName("invoiceNumber")
    val invoiceNumber: String?,

    /**
     * Note/description of the transaction (user input)
     */
    @SerialName("note")
    val note: String,

    /**
     * Transaction creation date
     */
    @SerialName("created")
    val created: Date,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long
) : BaseEntityDto() {

    @Serializable
    enum class Type {
        /**
         * Purchase of products
         */
        @SerialName("purchase")
        PURCHASE,

        /**
         * Transfer between two warehouses
         */
        @SerialName("transfer")
        TRANSFER,

        /**
         * Correction
         */
        @SerialName("correction")
        CORRECTION,

        /**
         * Stock taking
         */
        @SerialName("stockTaking")
        STOCK_TAKING
    }
}
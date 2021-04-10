@file:UseSerializers(
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity.markeeta

import com.dotypos.lib.migration.dto.entity.BaseEntityDto
import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithVersion
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

/**
 * Orders in Markeeta.
 */
@Serializable
data class OrderMarkeetaMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * Number of order.
     */
    @SerialName("orderNumber")
    val orderNumber: String?,

    /**
     * Date of order.
     */
    @SerialName("orderDate")
    val orderDate: Date?,

    /**
     * Order invoiced date.
     */
    @SerialName("invoiceDate")
    val invoiceDate: Date?,

    /**
     * Order payment date.
     */
    @SerialName("paymentDate")
    val paymentDate: Date?,

    /**
     * Order delivery date.
     */
    @SerialName("deliveryDate")
    val deliveryDate: Date?,

    /**
     * Additional info about order.
     */
    @SerialName("note")
    val note: String?,

    /**
     * Merchant ID in Markeeta (unique id for company in Markeeta).
     */
    @SerialName("merchantIdMarkeeta")
    val merchantIdMarkeeta: Long,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto()
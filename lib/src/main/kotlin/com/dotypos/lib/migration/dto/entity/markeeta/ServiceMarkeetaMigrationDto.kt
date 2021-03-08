@file:UseSerializers(
    DateSerializer::class,
    BigDecimalSerializer::class
)

package com.dotypos.lib.migration.dto.entity.markeeta

import com.dotypos.lib.migration.dto.entity.BaseEntityDto
import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithName
import com.dotypos.lib.migration.dto.entity.iface.WithVersion
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal
import java.util.Date

/**
 * Additional payable services for company in Markeeta.
 */
@Serializable
data class ServiceMarkeetaMigrationDto (
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    /**
     * Service price.
     */
    @SerialName("price")
    val price: BigDecimal,

    /**
     * Service periodicity in months.
     */
    @SerialName("monthPeriodicity")
    val monthPeriodicity: Long,

    /**
     * Is active.
     */
    @SerialName("isActive")
    val isActive: Boolean,

    /**
     * Invoiced until.
     */
    @SerialName("invoicedUntil")
    val invoicedUntil: Date?,

    /**
     * Id id SIM card (if service is sim card).
     */
    @SerialName("simIccid")
    val simIccid: String?,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName
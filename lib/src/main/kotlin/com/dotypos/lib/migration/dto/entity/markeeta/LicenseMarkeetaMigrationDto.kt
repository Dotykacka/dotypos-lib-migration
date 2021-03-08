@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity.markeeta

import com.dotypos.lib.migration.dto.entity.BaseEntityDto
import com.dotypos.lib.migration.dto.entity.iface.WithCurrency
import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithVersion
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal
import java.util.Date

/**
 * Markeeta license.
 */
@Serializable
data class LicenseMarkeetaMigrationDto (
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * License key used for migration to DTK.
     */
    @SerialName("licenseKey")
    val licenseKey: String,

    /**
     * Type of license before migration.
     */
    @SerialName("originalLicenseType")
    val originalLicenseType: String,

    /**
     * Invoiced until.
     */
    @SerialName("invoicedUntil")
    val invoicedUntil: Date,

    /**
     * Price.
     */
    @SerialName("price")
    val price: BigDecimal,

    @SerialName(WithCurrency.SERIALIZED_NAME)
    override val currency: String,

    /**
     * License periodicity in months.
     */
    @SerialName("monthPeriodicity")
    val monthPeriodicity: Long,

    /**
     * Shop ID in Markeeta (equivalent of cloudId in DTK).
     */
    @SerialName("shopIdMarkeeta")
    val shopIdMarkeeta: Long,

    /**
     * Terminal ID in Markeeta (equivalent of branchId in DTK).
     */
    @SerialName("terminalIdMarkeeta")
    val terminalIdMarkeeta: Long,

    /**
     * Additional info about license.
     */
    @SerialName("note")
    val note: String?,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithCurrency
@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.UseSerializers


import com.dotypos.lib.migration.dto.entity.iface.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SupplierMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * Name of supplier (company name or given + family name)
     */
    @SerialName("name")
    override val name: String,

    /**
     * ID of company (CZ = IČO)
     */
    @SerialName("companyId")
    val companyId: String?,

    /**
     * Additional companyId, used only in some countries
     */
    @SerialName("companyId2")
    val companyId2: String?,

    /**
     * VAT ID of company (CZ = DIČ)
     */
    @SerialName("vatId")
    val vatId: String?,

    /**
     * Address lines (street with number, freeform text)
     */
    @SerialName("address")
    val address: List<String>,

    /**
     * City
     */
    @SerialName("city")
    val city: String,

    /**
     * ZIP code
     */
    @SerialName("zip")
    val zip: String,

    @SerialName(WithCountry.SERIALIZED_NAME)
    override val country: String = WithCountry.DEFAULT_COUNTRY,

    @SerialName(WithEmail.SERIALIZED_NAME)
    override val email: String?,

    @SerialName(WithPhone.SERIALIZED_NAME)
    override val phone: String?,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithEmail, WithPhone, WithName, WithCountry, Deletable

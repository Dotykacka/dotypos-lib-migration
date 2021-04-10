package com.dotypos.lib.migration.dto.entity.markeeta

import com.dotypos.lib.migration.dto.entity.BaseEntityDto
import com.dotypos.lib.migration.dto.entity.iface.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Address for Markeeta company.
 */
@Serializable
data class AddressMarkeetaMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    /**
     * Street.
     */
    @SerialName("street")
    val street: String,

    /**
     * City.
     */
    @SerialName("city")
    val city: String,

    /**
     * ZIP code.
     */
    @SerialName("zipCode")
    val zipCode: String,

    /**
     * Region - e.g. Karlovarský kraj.
     */
    @SerialName("region")
    val region: String?,

    @SerialName(WithCountry.SERIALIZED_NAME)
    override val country: String,

    @SerialName(WithTags.SERIAL_NAME)
    override val tags: List<String>,

    /**
     * Additional info for address.
     */
    @SerialName("note")
    val note: String?,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName, WithTags, WithCountry
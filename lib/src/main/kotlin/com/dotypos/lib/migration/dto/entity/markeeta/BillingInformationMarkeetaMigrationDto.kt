package com.dotypos.lib.migration.dto.entity.markeeta

import com.dotypos.lib.migration.dto.entity.BaseEntityDto
import com.dotypos.lib.migration.dto.entity.iface.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Billing information about Markeeta company.
 */
@Serializable
data class BillingInformationMarkeetaMigrationDto (
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    @SerialName(WithEmail.SERIALIZED_NAME)
    override val email: String?,

    @SerialName(WithPhone.SERIALIZED_NAME)
    override val phone: String?,

    /**
     * Is Active.
     */
    @SerialName("isActive")
    val isActive: Boolean,

    /**
     * Additional information.
     */
    @SerialName("note")
    val note: String?,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName, WithEmail, WithPhone
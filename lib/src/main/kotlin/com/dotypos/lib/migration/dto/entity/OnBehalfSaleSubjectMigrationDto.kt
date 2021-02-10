package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Subjects to On Behalf Sale feature
 */
@Serializable
data class OnBehalfSaleSubjectMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    /**
     * VAT ID of company (CZ = DIČ)
     */
    @SerialName("vatId")
    val vatId: String,

    /**
     * Should be company treated as VAT payer
     */
    @SerialName("vatPayer")
    val isVatPayer: Boolean,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(Enablable.SERIAL_NAME)
    override val isEnabled: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName, Deletable, Enablable
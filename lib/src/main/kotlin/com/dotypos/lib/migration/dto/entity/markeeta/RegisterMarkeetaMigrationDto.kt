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
import java.util.Date

/**
 * Data about company from public company register.
 */
@Serializable
data class RegisterMarkeetaMigrationDto (
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * Address of business.
     */
    @SerialName("businessArea")
    val businessArea: String?,

    /**
     * Business owners names.
     */
    @SerialName("businessOwners")
    val businessOwners: String?,

    /**
     * Is Vat Payer.
     */
    @SerialName("isVatPayer")
    val isVatPayer: Boolean?,

    /**
     * Legal form of business (s.r.o. or a.s. in CZ)
     */
    @SerialName("legalForm")
    val legalForm: String?,

    /**
     * Date of business creation.
     */
    @SerialName("creationDate")
    val creationDate: Date?,

    /**
     * Additional info from public company register.
     */
    @SerialName("register")
    val register: String?,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto()
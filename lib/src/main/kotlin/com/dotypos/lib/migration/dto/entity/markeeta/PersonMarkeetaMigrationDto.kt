@file:UseSerializers(
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity.markeeta

import com.dotypos.lib.migration.dto.entity.BaseEntityDto
import com.dotypos.lib.migration.dto.entity.iface.*
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

/**
 * Company person.
 */
@Serializable
data class PersonMarkeetaMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    @SerialName(WithPhone.SERIALIZED_NAME)
    override val phone: String,

    @SerialName(WithEmail.SERIALIZED_NAME)
    override val email: String,

    /**
     * Date of birth.
     */
    @SerialName("dateOfBirth")
    val dateOfBirth: Date?,

    /**
     * City.
     */
    @SerialName("residenceCity")
    val residenceCity: String,

    /**
     * Street.
     */
    @SerialName("residenceStreet")
    val residenceStreet: String,

    /**
     * ZIP code.
     */
    @SerialName("residenceZip")
    val residenceZip: String,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName, WithPhone, WithEmail
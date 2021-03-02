@file:UseSerializers(
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity.suc

import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.Date

@Serializable
data class PersonDto (

    @SerialName("name")
    val name: String,

    @SerialName("phone")
    val phone: String,

    @SerialName("email")
    val email: String,

    @SerialName("date_of_birth")
    val dateOfBirth: Date?,

    @SerialName("residence_city")
    val residenceCity: String,

    @SerialName("residence_street")
    val residenceStreet: String,

    @SerialName("residence_zip")
    val residenceZip: String
)
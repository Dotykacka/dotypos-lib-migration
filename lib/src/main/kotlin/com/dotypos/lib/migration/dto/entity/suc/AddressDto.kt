package com.dotypos.lib.migration.dto.entity.suc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto (

    @SerialName("nazev")
    val name: String,

    @SerialName("street")
    val street: String,

    @SerialName("city")
    val city: String,

    @SerialName("zip")
    val zipCode: String,

    @SerialName("kraj")
    val region: String?,

    @SerialName("state")
    val state: String,

    @SerialName("tags")
    val tags: String?,

    @SerialName("notes")
    val note: String?
)
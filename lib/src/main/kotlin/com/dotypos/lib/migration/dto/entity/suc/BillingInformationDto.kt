package com.dotypos.lib.migration.dto.entity.suc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BillingInformationDto (

    @SerialName("jmeno")
    val name: String,

    @SerialName("email")
    val email: String?,

    @SerialName("phone")
    val phone: String?,

    @SerialName("aktivni")
    val active: Boolean,

    @SerialName("poznamka")
    val note: String?
)
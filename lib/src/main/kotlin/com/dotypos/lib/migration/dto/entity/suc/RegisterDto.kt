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
data class RegisterDto (

    @SerialName("business_area")
    val businessArea: String?,

    @SerialName("business_owners")
    val businessOwners: String?,

    @SerialName("vat_payer")
    val vatPayer: Boolean?,

    @SerialName("pravni_forma")
    val legalForm: String?,

    @SerialName("datum_vzniku")
    val creationDate: Date?,

    @SerialName("rejstrik")
    val index: String?
)
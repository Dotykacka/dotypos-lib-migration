@file:UseSerializers(
    DateSerializer::class,
    BigDecimalSerializer::class
)

package com.dotypos.lib.migration.dto.entity.suc

import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal
import java.util.Date

@Serializable
data class ServiceDto (

    @SerialName("name")
    val name: String,

    @SerialName("cena")
    val price: BigDecimal,

    @SerialName("periodicita")
    val periodicity: Long,

    @SerialName("aktivni")
    val active: Boolean,

    @SerialName("fakturovano_do")
    val invoicedTo: Date?,

    @SerialName("ICID_simkarty")
    val simIccid: String?
)
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
data class OrderDto (

    @SerialName("cislo_objednavky")
    val orderNumber: String?,

    @SerialName("datum_objednavky")
    val orderDate: Date?,

    @SerialName("datum_vystaveni_faktury")
    val invoiceDate: Date?,

    @SerialName("datum_zaplaceni")
    val paymentDate: Date?,

    @SerialName("datum_dodani")
    val deliveryDate: Date?,

    @SerialName("poznamka")
    val note: String?,

    @SerialName("mechant_id_markeeta")
    val merchantIdMarkeeta: Long
)
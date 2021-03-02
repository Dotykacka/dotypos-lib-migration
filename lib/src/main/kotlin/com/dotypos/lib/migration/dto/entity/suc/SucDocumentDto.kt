@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity.suc

import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal
import java.util.*

@Serializable
data class SucDocumentDto (

    @SerialName("typ")
    val type: Type,

    @SerialName("nazev_dokumentu")
    val name: String,

    @SerialName("id_dokumentu")
    val id: String,

    @SerialName("url_dokumentu")
    val url: String,

    @SerialName("castka_bez_DPH")
    val priceWithoutVat: BigDecimal,

    @SerialName("castka_s_DPH")
    val priceWithVat: BigDecimal,

    @SerialName("datum_vytvoreni")
    val creationDate: Date,

    @SerialName("datum_zdanitelneho_plneni")
    val dateOfTaxableSupply: Date,

    @SerialName("datum_splatnosti")
    val dueDate: Date,
) {
    @Serializable
    enum class Type {

        @SerialName("Faktura licence")
        LICENCE_INVOICE,

        @SerialName("Faktura SIM karta")
        SIM_INVOICE,

        @SerialName("Faktura doplňkový balíček")
        PACKAGE_INVOICE,

        @SerialName("Splátkový kalendář")
        PAYMENT_SCHEDULE
    }
}
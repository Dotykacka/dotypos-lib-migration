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
import java.util.Date

@Serializable
data class LicenceDto (

    @SerialName("licencni_klic")
    val licenceKey: String,

    @SerialName("puvodni_typ_licence")
    val originLicenceType: String,

    @SerialName("fakturovano_do")
    val invoicedTo: Date,

    @SerialName("cena")
    val price: BigDecimal,

    @SerialName("currency")
    val currency: String,

    @SerialName("delka_licence")
    val licenceLength: String,

    @SerialName("shop_id_markeeta")
    val shopIdMarkeeta: Long,

    @SerialName("terminal_id_markeeta")
    val terminalIdMarkeeta: Long,

    @SerialName("poznamka")
    val note: String?
)
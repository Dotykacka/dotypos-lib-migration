package com.dotypos.lib.migration.dto.config

import com.dotypos.lib.migration.dto.entity.iface.Enablable
import com.dotypos.lib.migration.dto.entity.iface.SellerRelated
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Configuration of CZ fiscalization for the [seller][SellerMigrationDto]
 */
@Serializable
data class CzFiscalizationConfiguration(

    /**
     * Base64 encoded keystore file in default EET format repackaged with custom password
     * Custom password is craeted by `sha1hex(PosMigrationDto.Metadata.migrationId + SALT)`
     * where `SALT` will be provided separately
     */
    @SerialName("data")
    val data: String,

    /**
     * VAT ID of seller, validate with [REGEX_VAT_ID]
     */
    @SerialName("vatId")
    val vatId: String,

    /**
     * Name of sale location - `id_provoz`, validated with [REGEX_SALE_LOCATION]
     */
    @SerialName("saleLocationId")
    val saleLocationId: Long,

    @SerialName(SellerRelated.SERIAL_NAME)
    override val sellerId: Long?,

    /**
     * According to CZ law changes, some subjects are not mandatory to print VAT ID on the receipt.
     */
    @SerialName("printVatId")
    val printVatId: Boolean,

    /**
     * Usage of EET simplified mode in communication with Ministry of Finance registry
     */
    @SerialName("simplifiedMode")
    val simplifiedMode: Boolean,

    /**
     * Allow issuing receipts without fiscalization
     */
    @SerialName("allowWithoutFiscalization")
    val allowWithoutFiscalization: Boolean,

    /**
     * If set to false, only cash payment methods will be fiscalized.
     * [allowWithoutFiscalization] must be set to `true`
     */
    @SerialName("fiscalizeCashless")
    val fiscalizeCashless: Boolean,

    @SerialName(Enablable.SERIAL_NAME)
    override val isEnabled: Boolean,
) : SellerRelated, Enablable {
    companion object {
        val REGEX_VAT_ID = "^CZ[0-9]{8,10}$".toRegex()
        val REGEX_SALE_LOCATION = "^[1-9][0-9]{0,5}$".toRegex()
    }
}
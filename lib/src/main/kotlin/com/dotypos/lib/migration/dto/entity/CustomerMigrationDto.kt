@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.*
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal
import java.util.*

@Serializable
data class CustomerMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * First (and middle name) of customer
     */
    @SerialName("firstName")
    val firstName: String,

    /**
     * Last name of customer
     */
    @SerialName("lastName")
    val lastName: String,

    /**
     * Name of company
     */
    @SerialName("companyName")
    val companyName: String,

    /**
     * ID of company (CZ = IČO)
     */
    @SerialName("companyId")
    val companyId: String,

    /**
     * Additional companyId, used only in some countries
     */
    @SerialName("companyId2")
    val companyId2: String?,

    /**
     * VAT ID of company (CZ = DIČ)
     */
    @SerialName("vatId")
    val vatId: String,

    @SerialName(WithEmail.SERIALIZED_NAME)
    override val email: String?,

    @SerialName(WithPhone.SERIALIZED_NAME)
    override val phone: String?,

    /**
     * Address lines (street with number, freeform text)
     */
    @SerialName("address")
    val address: List<String>,

    /**
     * City
     */
    @SerialName("city")
    val city: String,

    /**
     * ZIP code
     */
    @SerialName("zip")
    val zip: String,

    @SerialName(WithCountry.DEFAULT_COUNTRY)
    override val country: String = DEFAULT_COUNTRY,

    /**
     * Barcode of customer account (used for loyalty cards or chips)
     */
    @SerialName("barcode")
    val barcode: String,

    /**
     * Date of customer account expiration (used for memberships or limited access)
     */
    @SerialName("expirationDate")
    val expirationDate: Date,

    /**
     * Printable customer note
     */
    @SerialName("note")
    val note: String,

    /**
     * Current amount of customer points
     */
    @SerialName("points")
    val points: BigDecimal,

    @SerialName(WithTags.SERIAL_NAME)
    override val tags: List<String>,

    /**
     * ID of [discount group][CustomerDiscountGroupMigrationDto]
     */
    @SerialName("discountGroupId")
    val discountGroupId: Long?,

    @SerialName(SellerRelated.SERIAL_NAME)
    override val sellerId: Long?,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), SellerRelated, WithTags, WithEmail, WithPhone, WithCountry, Deletable {
    companion object {
        const val DEFAULT_COUNTRY = "CZ"
    }
}
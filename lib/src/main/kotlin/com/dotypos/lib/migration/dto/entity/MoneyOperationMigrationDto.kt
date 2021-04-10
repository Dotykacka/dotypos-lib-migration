@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.*
import com.dotypos.lib.migration.dto.enumerate.PaymentMethod
import com.dotypos.lib.migration.dto.validation.validateCurrency
import com.dotypos.lib.migration.dto.validation.validateId
import com.dotypos.lib.migration.dto.validation.validateVersion
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import com.dotypos.validator.validation.hasSize
import com.dotypos.validator.validationOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal
import java.util.*

@Serializable
data class MoneyOperationMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(SellerRelated.SERIAL_NAME)
    override val sellerId: Long?,

    /**
     * ID of [employee][EmployeeMigrationDto] which created record
     */
    @SerialName("employeeId")
    val employeeId: Long,

    /**
     * ID of related [document][DocumentMigrationDto]
     */
    @SerialName("documentId")
    val documentId: Long?,

    /**
     * Type of transaction
     */
    @SerialName("type")
    val type: Type,

    /**
     * Payment method of transaction
     */
    @SerialName("paymentMethod")
    val paymentMethod: PaymentMethod,

    /**
     * Amount of money change in primary currency (positive if received, negative if expended)
     */
    @SerialName("primaryAmount")
    val primaryAmount: BigDecimal,

    /**
     * Amount of money change in [currency], correctly rounded (positive if received, negative if expended)
     */
    @SerialName("amount")
    val amount: BigDecimal,

    @SerialName(WithCurrency.SERIALIZED_NAME)
    override val currency: String,

    /**
     * Rounded displayable exchange rate
     * 250 CZK (primary) ~ 10 USD -> 0.04
     * 10 USD (primary) ~ 250 CZK -> 25
     */
    @SerialName("exchangeRate")
    val exchangeRate: BigDecimal,

    /**
     * Money transaction note
     */
    @SerialName("note")
    val note: String,

    /**
     * Date when money transaction was performed
     */
    @SerialName("created")
    val created: Date,

    /**
     * Detailed data about card payment from the connected payment terminal
     */
    @SerialName("cardPaymentData")
    val cardPaymentData: CardPaymentData?,

    @SerialName(WithTags.SERIAL_NAME)
    override val tags: List<String>,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), SellerRelated, WithCurrency, WithTags {

    init {
        validateId()
        validateCurrency()
        validationOf(MoneyOperationMigrationDto::note).hasSize(max = 1000)
        validateVersion()
    }

    @Serializable
    enum class Type {
        @SerialName("sale")
        SALE,

        @SerialName("refund")
        REFUND,

        @SerialName("registerOpen")
        REGISTER_OPEN,

        @SerialName("registerClose")
        REGISTER_CLOSE,

        @SerialName("cashIn")
        CASH_IN,

        @SerialName("cashOut")
        CASH_OUT,

        @SerialName("writeOff")
        WRITE_OFF,
    }

    @Serializable
    data class CardPaymentData(
        /**
         * Name of payment terminal provider
         */
        @SerialName("provider")
        val provider: String = DEFAULT_PROVIDER,

        /**
         * Merchant ID provided by payment terminal
         */
        @SerialName("merchantId")
        val merchantId: String = DEFAULT_MERCHANT_ID,

        /**
         * Identification of payer
         */
        @SerialName("payerId")
        val payerId: String = DEFAULT_PAYER_ID,

        /**
         * Transaction code if available
         */
        @SerialName("transactionCode")
        val transactionCode: String = DEFAULT_TRANSACTION_CODE
    ) {

        init {
            validationOf(CardPaymentData::provider).hasSize(max = 100)
            validationOf(CardPaymentData::merchantId).hasSize(max = 100)
            validationOf(CardPaymentData::payerId).hasSize(max = 100)
            validationOf(CardPaymentData::transactionCode).hasSize(max = 100)
        }

        companion object {
            const val DEFAULT_PROVIDER = "unknown"
            const val DEFAULT_MERCHANT_ID = "unknown"
            const val DEFAULT_PAYER_ID = "unknown"
            const val DEFAULT_TRANSACTION_CODE = "unknown"
        }
    }
}
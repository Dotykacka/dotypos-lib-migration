@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.config

import com.dotypos.lib.migration.dto.entity.iface.WithCurrency
import com.dotypos.lib.migration.dto.validation.validateCurrency
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import com.dotypos.validator.validation.isBetween
import com.dotypos.validator.validation.isGreaterThan
import com.dotypos.validator.validationOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
data class AlternativeCurrencyConfiguration(
    @SerialName(WithCurrency.SERIALIZED_NAME)
    override val currency: String,

    /**
     * If set to true cash will be always returned in primary currency.
     */
    @SerialName("returnInPrimary")
    val returnInPrimary: Boolean,

    /**
     * Number of decimal digits used for rounding of payments in alternative currency.
     * Is be used only if [returnInPrimary] is set to false.
     */
    @SerialName("roundingDecimals")
    val roundingDecimals: Int,

    /**
     * Exchange rate amount, used primarily to better display of conversion.
     * Usage:
     * (exchangeRateAmount) currency == (exchangeRate) primaryCurrency
     *          100                   JPY          ==     20.486         CZK
     *           1                    EUR          ==     25.970         CZK
     */
    @SerialName("exchangeRateamount")
    val exchangeRateAmount: BigDecimal,

    /**
     * Exchange rate to primary currency.
     */
    @SerialName("exchangeRate")
    val exchangeRate: BigDecimal,
) : WithCurrency {
    init {
        validateCurrency()
        validationOf(AlternativeCurrencyConfiguration::roundingDecimals)
            .isBetween(0, 2)
        validationOf(AlternativeCurrencyConfiguration::exchangeRateAmount)
            .isGreaterThan(BigDecimal.ZERO)
        validationOf(AlternativeCurrencyConfiguration::exchangeRate)
            .isGreaterThan(BigDecimal.ZERO)
    }
}
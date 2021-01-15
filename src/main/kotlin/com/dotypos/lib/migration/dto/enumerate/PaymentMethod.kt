package com.dotypos.lib.migration.dto.enumerate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * List of supported payment methods
 * @param isCash is payment method treated as a cash (counted to cash register status, sent to EET)
 */
@Serializable
enum class PaymentMethod(val isCash: Boolean) {
    @SerialName("cash")
    CASH(isCash = true),

    @SerialName("card")
    CARD(isCash = false),

    @SerialName("check")
    CHECK(isCash = true),

    @SerialName("mealVoucher")
    MEAL_VOUCHER(isCash = true),

    @SerialName("mealVoucherElectronic")
    MEAL_VOUCHER_ELECTRONIC(isCash = false),

    @SerialName("voucher")
    VOUCHER(isCash = true),

    @SerialName("bankTransfer")
    BANK_TRANSFER(isCash = false),

    @SerialName("writeOff")
    WRITE_OFF(isCash = true)
}
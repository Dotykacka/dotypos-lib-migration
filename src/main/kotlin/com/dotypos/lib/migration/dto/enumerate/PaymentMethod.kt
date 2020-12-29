package com.dotypos.lib.migration.dto.enumerate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PaymentMethod {
    @SerialName("cash")
    CASH,

    @SerialName("card")
    CARD,

    @SerialName("check")
    CHECK,

    @SerialName("mealVoucher")
    MEAL_VOUCHER,

    @SerialName("mealVoucherElectronic")
    MEAL_VOUCHER_ELECTRONIC,

    @SerialName("voucher")
    VOUCHER,

    @SerialName("bankTransfer")
    BANK_TRANSFER,

    @SerialName("writeOff")
    WRITE_OFF
}
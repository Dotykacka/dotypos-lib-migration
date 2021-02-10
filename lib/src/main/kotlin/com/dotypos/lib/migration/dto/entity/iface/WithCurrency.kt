package com.dotypos.lib.migration.dto.entity.iface

import kotlinx.serialization.SerialName

interface WithCurrency {
    /**
     * Currency code [ISO 4217 format](https://www.iso.org/iso-4217-currency-codes.html)
     */
    @SerialName(SERIALIZED_NAME)
    val currency: String

    companion object {
        const val SERIALIZED_NAME = "currency"
    }
}
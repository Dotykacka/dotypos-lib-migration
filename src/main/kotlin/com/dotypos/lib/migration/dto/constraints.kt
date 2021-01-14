package com.dotypos.lib.migration.dto

val LONG_SAFE_RANGE = -9007199254740991L..9007199254740991L

object CzFiscalizationConstraints {

    val BUSINESS_PREMISES_ID_FORMAT = "^[1-9][0-9]{0,5}$".toRegex()

    val CASH_REGISTER_ID_FORMAT = "^[0-9a-zA-Z.,:;/#\\-_]{1,20}$".toRegex()

    val SERIAL_NUMBER_FORMAT = "^[0-9a-zA-Z.,:;/#\\-_]{1,20}$".toRegex()

    val FIK_FORMAT =
        "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}(-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}-[0-9a-fA-F]{2})?$".toRegex()

    val BKP_FORMAT =
        "^[0-9a-fA-F]{8}-[0-9a-fA-F]{8}(-[0-9a-fA-F]{8}-[0-9a-fA-F]{8}-[0-9a-fA-F]{8})?$".toRegex()
}
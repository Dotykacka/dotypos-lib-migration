package com.dotypos.lib.migration.dto.entity.iface

import com.dotypos.lib.migration.dto.LONG_SAFE_RANGE
import kotlinx.serialization.SerialName
import org.valiktor.Validator
import org.valiktor.functions.isBetween
import org.valiktor.validate

interface WithExternalEdiId {

    /**
     * External id of related EDI entity
     */
    @SerialName(SERIAL_NAME)
    val externalEdiId: String?

    companion object {
        const val SERIAL_NAME = "externalEdiId"
    }
}
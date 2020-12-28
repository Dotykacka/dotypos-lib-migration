package com.dotypos.lib.migration.dto.entity.iface

import com.dotypos.lib.migration.dto.LONG_SAFE_RANGE
import kotlinx.serialization.SerialName
import org.valiktor.Validator
import org.valiktor.functions.isBetween
import org.valiktor.validate

interface WithId {

    /**
     * Unique id of entity constrained by [LONG_SAFE_RANGE].
     */
    @SerialName(SERIAL_NAME)
    val id: Long

    companion object {
        const val SERIAL_NAME = "id"
    }
}
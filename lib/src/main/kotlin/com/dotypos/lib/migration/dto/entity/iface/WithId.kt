package com.dotypos.lib.migration.dto.entity.iface

import com.dotypos.lib.migration.dto.LONG_SAFE_RANGE
import kotlinx.serialization.SerialName

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
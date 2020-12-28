package com.dotypos.lib.migration.dto.entity.iface

import kotlinx.serialization.SerialName

interface Enablable {
    /**
     * Is entity enabled for public usage
     */
    @SerialName(SERIALIZED_NAME)
    val isEnabled: Boolean

    companion object {
        const val SERIALIZED_NAME = "enabled"
    }
}
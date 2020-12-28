package com.dotypos.lib.migration.dto.entity.iface

import kotlinx.serialization.SerialName

interface Deletable {
    /**
     * Entity deletion info
     */
    @SerialName(SERIAL_NAME)
    val isDeleted: Boolean

    companion object {
        const val SERIAL_NAME = "deleted"
    }
}
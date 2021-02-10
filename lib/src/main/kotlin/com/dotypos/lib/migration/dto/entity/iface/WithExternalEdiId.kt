package com.dotypos.lib.migration.dto.entity.iface

import kotlinx.serialization.SerialName

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
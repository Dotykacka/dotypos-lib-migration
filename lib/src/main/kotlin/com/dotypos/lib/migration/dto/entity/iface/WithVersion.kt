package com.dotypos.lib.migration.dto.entity.iface

import kotlinx.serialization.SerialName

interface WithVersion {

    /**
     * Version of the object - from our perspective it is Unix Timestamp of last
     * modification, but any number lower than current Unix Timestamp is suitable.
     */
    @SerialName(SERIAL_NAME)
    val version: Long

    companion object {
        const val SERIAL_NAME = "version"
        const val DEFAULT_VALUE = 0L
    }
}
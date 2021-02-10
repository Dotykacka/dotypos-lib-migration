package com.dotypos.lib.migration.dto.entity.iface

import kotlinx.serialization.SerialName

interface WithColor {
    /**
     * Hex representation of entity color used for display purposes.
     * Usage of colors from Material Design palette recommended.
     *
     * Format: with leading '#' #AARRGGBB and #RRGGBB formats supported
     */
    @SerialName(SERIALIZED_NAME)
    val hexColor: String

    companion object {
        const val SERIALIZED_NAME = "hexColor"
    }
}
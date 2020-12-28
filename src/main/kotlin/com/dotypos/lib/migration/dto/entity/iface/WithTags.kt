package com.dotypos.lib.migration.dto.entity.iface

import kotlinx.serialization.SerialName

interface WithTags {

    /**
     * List of unique tags. Meaning could differ regarding the entity.
     * Primary usage is for print/report filtering or special behavior of sale items.
     */
    @SerialName(SERIAL_NAME)
    val tags: List<String>

    companion object {
        const val SERIAL_NAME = "tags"
    }
}
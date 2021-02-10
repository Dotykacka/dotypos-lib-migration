package com.dotypos.lib.migration.dto.entity.iface

interface WithCustomPrintName : WithName {
    /**
     * Print name of entity - used on official printouts.
     *
     * - `null` if no printName presented: the [name] will be used instead,
     * - not empty value required otherwise.
     */
    val printName: String?

    companion object {
        const val SERIAL_NAME = "printName"
    }
}
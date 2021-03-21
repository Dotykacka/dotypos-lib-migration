package com.dotypos.lib.migration.dto.entity.iface

/**
 * Used for entities which can be hidden from sale point of view
 */
interface Displayable {
    /**
     * Indicates whether the entity should be displayed on the sale screen.
     */
    val display: Boolean

    companion object {
        const val SERIAL_NAME = "display"
    }
}
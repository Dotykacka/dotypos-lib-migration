package com.dotypos.lib.migration.dto.entity.iface

interface WithName {
    /**
     * Name of the entity
     */
    val name: String

    companion object {
        const val SERIAL_NAME = "name"
    }
}
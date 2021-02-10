package com.dotypos.lib.migration.dto.entity.iface

interface WithEmail {
    /**
     * Valid email contact or `null`
     */
    val email: String?

    companion object {
        const val SERIALIZED_NAME = "email"
    }
}
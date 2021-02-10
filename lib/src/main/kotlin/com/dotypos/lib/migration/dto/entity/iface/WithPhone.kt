package com.dotypos.lib.migration.dto.entity.iface

interface WithPhone {
    /**
     * Valid phone contact or `null`
     */
    val phone: String?

    companion object {
        const val SERIALIZED_NAME = "phone"
    }
}
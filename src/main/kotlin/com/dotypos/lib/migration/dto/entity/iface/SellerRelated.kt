package com.dotypos.lib.migration.dto.entity.iface

interface SellerRelated {
    /**
     * ID of related [SellerMigrationDto]
     */
    val sellerId: Long?

    companion object {
        const val SERIALIZED_NAME = "sellerId"
    }
}
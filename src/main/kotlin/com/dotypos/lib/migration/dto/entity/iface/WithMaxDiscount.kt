package com.dotypos.lib.migration.dto.entity.iface

import com.dotypos.lib.migration.extension.toColorInt
import kotlinx.serialization.SerialName
import java.math.BigDecimal

interface WithMaxDiscount {
    /**
     * Limitation of max discount allowed for entity. 'null' if not limitation should be applied.
     */
    @SerialName(SERIALIZED_NAME)
    val maxDiscount: BigDecimal?

    companion object {
        const val SERIALIZED_NAME = "maxDiscount"
    }
}
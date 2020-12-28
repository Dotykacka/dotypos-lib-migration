package com.dotypos.lib.migration.dto.enumerate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class MigrationType {
    /**
     * Local Markeeta POS migration (enumerates, configuration)
     */
    @SerialName("markeetaPos")
    MarkeetaPos,

    /**
     * Markeeta Cloud migration (transactional data)
     */
    @SerialName("markeetaCloud")
    MarkeetaCloud
}
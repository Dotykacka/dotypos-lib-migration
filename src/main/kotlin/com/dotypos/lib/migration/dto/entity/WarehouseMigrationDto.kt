package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithName
import com.dotypos.lib.migration.dto.entity.iface.WithVersion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WarehouseMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
): BaseEntityDto(), WithName
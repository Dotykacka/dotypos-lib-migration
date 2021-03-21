package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.*
import com.dotypos.lib.migration.dto.validation.requireName
import com.dotypos.lib.migration.dto.validation.validateId
import com.dotypos.lib.migration.dto.validation.validateVersion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    @SerialName(WithTags.SERIAL_NAME)
    override val tags: List<String>,

    @SerialName(Enablable.SERIAL_NAME)
    override val isEnabled: Boolean,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName, WithTags, Enablable, Deletable {
    init {
        validateId()
        requireName()
        validateVersion()
    }
}
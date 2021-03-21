package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithName
import com.dotypos.lib.migration.dto.entity.iface.WithVersion
import com.dotypos.lib.migration.dto.validation.isValidId
import com.dotypos.lib.migration.dto.validation.requireName
import com.dotypos.lib.migration.dto.validation.validateId
import com.dotypos.lib.migration.dto.validation.validateVersion
import com.dotypos.validator.validationOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Seller
 */
@Serializable
class SellerMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    /**
     * ID of employee bound with this seller (1:1 relation required).
     */
    @SerialName("employeeId")
    val employeeId: Long,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName {
    init {
        validateId()
        requireName()
        validationOf(SellerMigrationDto::employeeId).isValidId()
        validateVersion()
    }
}
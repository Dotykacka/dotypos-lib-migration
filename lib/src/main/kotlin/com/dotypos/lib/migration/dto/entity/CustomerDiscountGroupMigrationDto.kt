@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.Deletable
import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithName
import com.dotypos.lib.migration.dto.entity.iface.WithVersion
import com.dotypos.lib.migration.dto.validation.requireName
import com.dotypos.lib.migration.dto.validation.validateId
import com.dotypos.lib.migration.dto.validation.validateVersion
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import com.dotypos.validator.validation.isGreaterThanOrEqualTo
import com.dotypos.validator.validationOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
data class CustomerDiscountGroupMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    /**
     * Discount in percent to be applied to sale items
     */
    @SerialName("discountPercent")
    val discountPercent: BigDecimal,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName, Deletable {
    init {
        validateId()
        requireName()
        validationOf(CustomerDiscountGroupMigrationDto::discountPercent)
            .isGreaterThanOrEqualTo(BigDecimal.ZERO)
        validateVersion()
    }
}
@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.Deletable
import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithMeasurementUnit
import com.dotypos.lib.migration.dto.entity.iface.WithVersion
import com.dotypos.lib.migration.dto.enumerate.MigrationMeasurementUnit
import com.dotypos.lib.migration.dto.validation.isValidId
import com.dotypos.lib.migration.dto.validation.validateId
import com.dotypos.lib.migration.dto.validation.validateVersion
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import com.dotypos.validator.validation.areNotEqual
import com.dotypos.validator.validationOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
data class ProductIngredientMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * ID of parent product
     */
    @SerialName("parentProductId")
    val parentProductId: Long,

    /**
     * ID of ingredient product
     */
    @SerialName("ingredientProductId")
    val ingredientProductId: Long,

    /**
     * Quantity to be deducted on sale
     */
    @SerialName("quantity")
    val quantity: BigDecimal,

    /**
     * Unit to be used to calculate amount of deduction
     * (must be in the same [group][MigrationMeasurementUnit.Group] as measurementUnit of deducted product)
     */
    @SerialName(WithMeasurementUnit.SERIAL_NAME)
    override val measurementUnit: MigrationMeasurementUnit,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithMeasurementUnit, Deletable {
    init {
        validateId()
        validationOf(ProductIngredientMigrationDto::parentProductId).isValidId()
        validationOf(ProductIngredientMigrationDto::ingredientProductId).isValidId()
        validationOf(
            ProductIngredientMigrationDto::parentProductId,
            ProductIngredientMigrationDto::ingredientProductId
        ).areNotEqual()
        validateVersion()
    }
}
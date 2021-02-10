@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.*
import com.dotypos.lib.migration.dto.validation.isValidId
import com.dotypos.lib.migration.dto.validation.requireValidId
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isPositiveOrZero
import org.valiktor.validate
import java.math.BigDecimal

/**
 * Product sale category
 */
@Serializable()
data class CategoryMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithExternalEdiId.SERIAL_NAME)
    override val externalEdiId: String?,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    @SerialName(WithColor.SERIALIZED_NAME)
    override val hexColor: String,

    /**
     * Default VAT rate which will be used for newly created products in category or
     * as VAT rate on [Instant Sale screen](http://manual.dotykacka.cz/index.html?okamzityprodej.html).
     *
     * Special values:
     * - `null` = don't use default VAT rate
     * - `-1` = Exempted VAT rate
     */
    @SerialName("defaultVatRate")
    val defaultVatRate: BigDecimal?,

    @SerialName(WithMaxDiscount.SERIALIZED_NAME)
    override val maxDiscount: BigDecimal?,

    /**
     * ID of default [course][CourseMigrationDto].
     */
    @SerialName("defaultCourseId")
    val defaultCourseId: Long?,

    @SerialName(WithTags.SERIAL_NAME)
    override val tags: List<String>,

    /**
     * Disable fiscalization for all items presented in category
     */
    @SerialName("fiscalizationDisabled")
    val fiscalizationDisabled: Boolean,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long = WithVersion.DEFAULT_VALUE,
) :
    BaseEntityDto(),
    WithExternalEdiId,
    WithName,
    WithColor,
    WithMaxDiscount,
    WithTags,
    Deletable {

    init {
        requireValidId(CategoryMigrationDto::id)
        validate(this) {
            validate(CategoryMigrationDto::id).isValidId()
            validate(CategoryMigrationDto::name).isNotEmpty()
            validate(CategoryMigrationDto::version).isPositiveOrZero()
        }
    }
}
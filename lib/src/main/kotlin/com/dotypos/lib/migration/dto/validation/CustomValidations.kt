package com.dotypos.lib.migration.dto.validation

import com.dotypos.lib.migration.dto.LONG_SAFE_RANGE
import com.dotypos.lib.migration.dto.entity.BaseEntityDto
import com.dotypos.lib.migration.dto.entity.iface.*
import com.dotypos.validator.context.PropertyValidationContext
import com.dotypos.validator.validation.*
import com.dotypos.validator.validationOf
import java.math.BigDecimal
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

fun PropertyValidationContext<*, Long>.isValidId() =
    this.isBetween(LONG_SAFE_RANGE.first, LONG_SAFE_RANGE.last)

fun PropertyValidationContext<*, Long?>.isValidIdOrNull() =
    this.isBetweenOrNull(LONG_SAFE_RANGE.first, LONG_SAFE_RANGE.last)


fun <T : BaseEntityDto> PropertyValidationContext<*, out Collection<T>>.hasUniqueItemIds() =
    this.hasUnique(BaseEntityDto::id)

/**
 * Validates relation of items
 */
fun <T : Any, P : BaseEntityDto> PropertyValidationContext<*, out Iterable<T>>.validateRelationsTo(
    entitiesParent: Any? = parent,
    key: KProperty<Long?>,
    entities: KProperty<Iterable<P>>,
) = customValidation {
    val entityIds = entities.getter
        .also { it.isAccessible = true }
        .call(entitiesParent)
        .map { it.id }
    val keyGetter = key.getter.also { it.isAccessible = true }
    val missing = value.mapNotNull {
        val keyValue = keyGetter.call(it)
        when {
            keyValue == null -> null
            entityIds.contains(keyValue) -> null
            else -> keyValue to it
        }
    }
    if (missing.isEmpty()) {
        return@customValidation
    }
    throw MissingRelatedEntityValidationError(
        propertyParent = parent,
        propertyName = property.name,
        keyName = key.name,
        relatedPropertyParent = entitiesParent,
        relatedPropertyName = entities.name,
        relatedPropertyKeyName = "id",
        values = missing.map { it.second },
        keys = missing.map { it.first },
    )
}

fun <T : Any> PropertyValidationContext<*, out Iterable<T>>.expectSum(
    sumValueParent: Any? = parent,
    key: KProperty<BigDecimal>,
    sumValue: KProperty<BigDecimal>,
) = customValidation {
    val keyGetter = key.getter.also { it.isAccessible }
    val expectedValue = sumValue.getter.also { it.isAccessible }.call(sumValueParent)
    val sum = value.sumOf { keyGetter.call(it) }
    if (sum.compareTo(expectedValue) != 0) {
        throw UnexpectedSumValue(
            propertyParent = parent,
            itemsPropertyName = property.name,
            keyName = key.name,
            sumPropertyParent = sumValueParent,
            sumPropertyName = sumValue.name,
            values = value,
            calculated = sum,
            expected = expectedValue,
        )
    }
}

fun <T : WithId> T.validateId() = validationOf(WithId::id).isValidId()
fun <T : WithName> T.requireName() = validationOf(WithName::name).isNotEmpty()
fun <T : BaseEntityDto> T.validateVersion() = validationOf(BaseEntityDto::version).isPositiveOrZero()

fun <T : WithColor> T.validateColor() = validationOf(WithColor::hexColor).isValidColor(requireLeadingHash = true)
fun <T : WithCurrency> T.validateCurrency() = validationOf(WithCurrency::currency).isValidCurrency()
fun <T : WithCountry> T.validateCountry() = validationOf(WithCountry::country).isValidCountry()
fun <T : SellerRelated> T.validateSellerId() = validationOf(SellerRelated::sellerId).isValidIdOrNull()
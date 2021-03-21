package com.dotypos.validator.validation

import com.dotypos.validator.UniqueConstraintValidationError
import com.dotypos.validator.constraint.Contains
import com.dotypos.validator.constraint.IsUnique
import com.dotypos.validator.context.DualPropertyValidationContext
import com.dotypos.validator.context.PropertyValidationContext
import com.dotypos.validator.isValid
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

fun <T, V, P> PropertyValidationContext<T, out Collection<V>>.hasUnique(childProperty: KProperty<P>): PropertyValidationContext<T, out Collection<V>> {
    val getter = childProperty.getter
    getter.isAccessible = true
    val existingValues = mutableMapOf<P, V>()
    value.forEachIndexed { index, it ->
        val childPropertyValue = getter.call(it)
        if (existingValues.containsKey(childPropertyValue)) {
            val otherValue = existingValues[childPropertyValue]
            setError(
                UniqueConstraintValidationError(
                    parent = parent,
                    propertyName = property.name,
                    propertyIndex = index,
                    childPropertyName = childProperty.name,
                    constraint = IsUnique,
                    childPropertyValue = childPropertyValue.toString(),
                )
            )
        }
        existingValues[childPropertyValue] = it
    }
    return this
}

fun <T, P1, P2 : Collection<P1>> DualPropertyValidationContext<T, P1, P2>.contains() =
    isValid(Contains) {
        true
    }
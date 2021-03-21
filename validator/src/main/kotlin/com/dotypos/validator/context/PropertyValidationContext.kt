package com.dotypos.validator.context

import com.dotypos.validator.ErrorCollector
import com.dotypos.validator.SimpleConstraintValidationError
import com.dotypos.validator.ValidationError
import com.dotypos.validator.constraint.ValidationConstraint
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

class PropertyValidationContext<T, P>(
    val parent: T,
    val property: KProperty<P>
) {
    val value: P
        get() {
            property.isAccessible = true
            return property.getter.call(parent)
        }

    fun setError(error: ValidationError) {
        if (ErrorCollector.active) {
            ErrorCollector += error
        } else {
            throw error
        }
    }

    fun simpleValidationError(constraint: ValidationConstraint, explanation: String? = null) {
        SimpleConstraintValidationError(
            parent = parent,
            propertyName = property.name,
            propertyValue = value.toString(),
            constraint = constraint,
            explanation = explanation ?: constraint.description,
        ).also(::setError)
    }

    fun customValidation(validation: PropertyValidationContext<T, P>.() -> Unit): PropertyValidationContext<T, P> {
        try {
            validation()
        } catch (e: ValidationError) {
            setError(e)
        }
        return this
    }
}
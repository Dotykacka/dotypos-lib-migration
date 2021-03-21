package com.dotypos.validator.context

import com.dotypos.validator.ErrorCollector
import com.dotypos.validator.SimpleDualConstraintValidationError
import com.dotypos.validator.ValidationError
import com.dotypos.validator.constraint.ValidationConstraint
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

class DualPropertyValidationContext<T, P1, P2>(
    val parent: T,
    val property1: KProperty<P1>,
    val property2: KProperty<P2>,
) {
    val value: Pair<P1, P2>
        get() {
            property1.isAccessible = true
            property2.isAccessible = true
            return property1.getter.call(parent) to property2.getter.call(parent)
        }

    fun setError(error: ValidationError) {
        if (ErrorCollector.active) {
            ErrorCollector += error
        } else {
            throw error
        }
    }

    fun simpleValidationError(constraint: ValidationConstraint, explanation: String? = null) {
        SimpleDualConstraintValidationError(
            parent = parent,
            property1Name = property1.name,
            property2Name = property2.name,
            property1Value = value.first.toString(),
            property2Value = value.second.toString(),
            constraint = constraint,
            explanation = explanation ?: constraint.description,
        ).also(::setError)
    }
}
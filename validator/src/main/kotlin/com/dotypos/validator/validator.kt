package com.dotypos.validator

import com.dotypos.validator.constraint.ValidationConstraint
import com.dotypos.validator.context.DualPropertyValidationContext
import com.dotypos.validator.context.PropertyValidationContext
import kotlin.reflect.KProperty


fun <T, P> T.validationOf(property: KProperty<P>, action: PropertyValidationContext<T, P>.() -> Unit = {}) =
    PropertyValidationContext(this, property)

fun <T, P1, P2> T.validationOf(
    property1: KProperty<P1>, property2: KProperty<P2>,
    action: DualPropertyValidationContext<T, P1, P2>.() -> Unit = {}
) = DualPropertyValidationContext(this, property1, property2)

fun <T, P> PropertyValidationContext<T, P>.isValid(
    constraint: ValidationConstraint,
    message: ((value: P) -> String?)? = null,
    validation: T.(value: P) -> Boolean,
): PropertyValidationContext<T, P> {
    if (!validation(parent, value)) {
        simpleValidationError(constraint, message?.invoke(value))
    }
    return this
}

fun <T, P1, P2> DualPropertyValidationContext<T, P1, P2>.isValid(
    constraint: ValidationConstraint,
    message: ((value: Pair<P1, P2>) -> String?)? = null,
    validation: T.(value: Pair<P1, P2>) -> Boolean,
): DualPropertyValidationContext<T, P1, P2> {
    if (!validation(parent, value)) {
        simpleValidationError(constraint, message?.invoke(value))
    }
    return this
}
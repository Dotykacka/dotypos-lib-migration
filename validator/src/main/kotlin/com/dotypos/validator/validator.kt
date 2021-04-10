package com.dotypos.validator

import com.dotypos.validator.constraint.ValidationConstraint
import com.dotypos.validator.context.DualPropertyValidationContext
import com.dotypos.validator.context.PropertyValidationContext
import kotlin.reflect.KProperty

public fun <T, P> T.validationOf(
    property: KProperty<P>,
    action: PropertyValidationContext<T, P>.() -> Unit = {},
): PropertyValidationContext<T, P> =
    PropertyValidationContext(this, property).also(action)

public fun <T, P1, P2> T.validationOf(
    property1: KProperty<P1>, property2: KProperty<P2>,
    action: DualPropertyValidationContext<T, P1, P2>.() -> Unit = {},
): DualPropertyValidationContext<T, P1, P2> =
    DualPropertyValidationContext(this, property1, property2).also(action)

public fun <T, P> PropertyValidationContext<T, P>.isValid(
    constraint: ValidationConstraint,
    message: ((value: P) -> String?)? = null,
    validation: T.(value: P) -> Boolean,
): PropertyValidationContext<T, P> {
    if (!validation(parent, value)) {
        simpleValidationError(constraint, message?.invoke(value))
    }
    return this
}

public fun <T, P1, P2> DualPropertyValidationContext<T, P1, P2>.isValid(
    constraint: ValidationConstraint,
    message: ((value: Pair<P1, P2>) -> String?)? = null,
    validation: T.(value: Pair<P1, P2>) -> Boolean,
): DualPropertyValidationContext<T, P1, P2> {
    if (!validation(parent, value)) {
        simpleValidationError(constraint, message?.invoke(value))
    }
    return this
}
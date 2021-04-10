package com.dotypos.validator.validation

import com.dotypos.validator.constraint.*
import com.dotypos.validator.context.PropertyValidationContext
import com.dotypos.validator.isValid

public fun <T, P : Comparable<P>> PropertyValidationContext<T, P>.isGreaterThan(value: P): PropertyValidationContext<T, P> =
    isValid(Greater(value)) { it > value }

public fun <T, P : Comparable<P>> PropertyValidationContext<T, P>.isGreaterThanOrEqualTo(value: P): PropertyValidationContext<T, P> =
    isValid(GreaterOrEqual(value)) { it >= value }

public fun <T, P : Comparable<P>> PropertyValidationContext<T, P>.isLessThan(value: P): PropertyValidationContext<T, P> =
    isValid(Less(value)) { it < value }

public fun <T, P : Comparable<P>> PropertyValidationContext<T, P>.isLessThanOrEqualTo(value: P): PropertyValidationContext<T, P> =
    isValid(LessOrEqual(value)) { it <= value }

/**
 * @param start inclusive
 * @param end inclusive
 */
public fun <T, P : Comparable<P>> PropertyValidationContext<T, P>.isBetween(start: P, end: P): PropertyValidationContext<T, P> =
    isValid(Between(start, end)) { it in start..end }

public fun <T, P : Comparable<P>> PropertyValidationContext<T, P?>.isBetweenOrNull(start: P, end: P): PropertyValidationContext<T, P?> =
    isValid(Between(start, end)) { it == null || it in start..end }
package com.dotypos.validator.validation

import com.dotypos.validator.constraint.*
import com.dotypos.validator.context.PropertyValidationContext
import com.dotypos.validator.isValid

fun <T, P : Comparable<P>> PropertyValidationContext<T, P>.isGreaterThan(value: P) =
    isValid(Greater(value)) { it > value }

fun <T, P : Comparable<P>> PropertyValidationContext<T, P>.isGreaterThanOrEqualTo(value: P) =
    isValid(GreaterOrEqual(value)) { it >= value }

fun <T, P : Comparable<P>> PropertyValidationContext<T, P>.isLessThan(value: P) =
    isValid(Less(value)) { it < value }

fun <T, P : Comparable<P>> PropertyValidationContext<T, P>.isLessThanOrEqualTo(value: P) =
    isValid(LessOrEqual(value)) { it <= value }

/**
 * @param start inclusive
 * @param end inclusive
 */
fun <T, P : Comparable<P>> PropertyValidationContext<T, P>.isBetween(start: P, end: P) =
    isValid(Between(start, end)) { it in start..end }

fun <T, P : Comparable<P>> PropertyValidationContext<T, P?>.isBetweenOrNull(start: P, end: P) =
    isValid(Between(start, end)) { it == null || it in start..end }
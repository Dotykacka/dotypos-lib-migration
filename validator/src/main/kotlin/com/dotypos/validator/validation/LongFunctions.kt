package com.dotypos.validator.validation

import com.dotypos.validator.constraint.GreaterOrEqual
import com.dotypos.validator.context.PropertyValidationContext
import com.dotypos.validator.isValid

fun <T> PropertyValidationContext<T, Long>.isPositiveOrZero() =
    isValid(GreaterOrEqual(0L)) { it >= 0L }
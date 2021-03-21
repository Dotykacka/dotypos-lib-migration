package com.dotypos.validator.validation

import com.dotypos.validator.constraint.EqualsDual
import com.dotypos.validator.constraint.NotEqualsDual
import com.dotypos.validator.context.DualPropertyValidationContext
import com.dotypos.validator.isValid

fun <T, P1, P2> DualPropertyValidationContext<T, P1, P2>.areEqual() =
    isValid(EqualsDual) {
        value.first == value.second
    }

fun <T, P1, P2> DualPropertyValidationContext<T, P1, P2>.areNotEqual() =
    isValid(NotEqualsDual) {
        value.first != value.second
    }
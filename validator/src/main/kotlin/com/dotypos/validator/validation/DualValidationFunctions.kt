package com.dotypos.validator.validation

import com.dotypos.validator.constraint.EqualsDual
import com.dotypos.validator.constraint.NotEqualsDual
import com.dotypos.validator.context.DualPropertyValidationContext
import com.dotypos.validator.isValid

public fun <T, P1, P2> DualPropertyValidationContext<T, P1, P2>.areEqual(): DualPropertyValidationContext<T, P1, P2> =
    isValid(EqualsDual) {
        value.first == value.second
    }

public fun <T, P1, P2> DualPropertyValidationContext<T, P1, P2>.areNotEqual(): DualPropertyValidationContext<T, P1, P2> =
    isValid(NotEqualsDual) {
        value.first != value.second
    }
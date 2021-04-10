package com.dotypos.validator.validation

import com.dotypos.validator.constraint.Empty
import com.dotypos.validator.constraint.NotBlank
import com.dotypos.validator.constraint.NotEmpty
import com.dotypos.validator.context.PropertyValidationContext
import com.dotypos.validator.isValid

public fun <T, P : CharSequence> PropertyValidationContext<T, P>.isEmpty(): PropertyValidationContext<T, P> =
    isValid(Empty) { it.isEmpty() }

public fun <T, P : CharSequence> PropertyValidationContext<T, P>.isNotEmpty(): PropertyValidationContext<T, P> =
    isValid(NotEmpty) { it.isNotEmpty() }

public fun <T, P : CharSequence> PropertyValidationContext<T, P>.isNotBlank(): PropertyValidationContext<T, P> =
    isValid(NotBlank) { it.isNotBlank() }
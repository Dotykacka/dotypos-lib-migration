package com.dotypos.validator.validation

import com.dotypos.validator.constraint.Empty
import com.dotypos.validator.constraint.NotBlank
import com.dotypos.validator.constraint.NotEmpty
import com.dotypos.validator.context.PropertyValidationContext
import com.dotypos.validator.isValid

fun <T, P : CharSequence> PropertyValidationContext<T, P>.isEmpty() =
    isValid(Empty) { it.isEmpty() }

fun <T, P : CharSequence> PropertyValidationContext<T, P>.isNotEmpty() =
    isValid(NotEmpty) { it.isNotEmpty() }

fun <T, P : CharSequence> PropertyValidationContext<T, P>.isNotBlank() =
    isValid(NotBlank) { it.isNotBlank() }
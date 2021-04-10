package com.dotypos.validator.validation

import com.dotypos.validator.constraint.*
import com.dotypos.validator.constraint.Currency
import com.dotypos.validator.context.PropertyValidationContext
import com.dotypos.validator.isValid
import java.util.*

public fun <T> PropertyValidationContext<T, String>.isValidColor(requireLeadingHash: Boolean = true): PropertyValidationContext<T, String> =
    isValid(HexColor(requireLeadingHash)) {
        if (requireLeadingHash) {
            it.matches(Regex("^#([0-9A-F]{6,8}|[0-9a-f]{6,8})$"))
        } else {
            it.matches(Regex("^([0-9A-F]{6,8}|[0-9a-f]{6,8})$"))
        }
    }

public fun <T> PropertyValidationContext<T, String>.isValidCurrency(): PropertyValidationContext<T, String> =
    isValid(Currency) {
        it.matches(Regex("^[A-Z]{3}$"))
    }

public fun <T> PropertyValidationContext<T, String>.isValidCountry(): PropertyValidationContext<T, String> =
    isValid(Country) {
        it.matches(Regex("^[A-Z]{2}$"))
    }

public fun <T> PropertyValidationContext<T, String>.isValidBase64(): PropertyValidationContext<T, String> =
    isValid(Base64Text) {
        kotlin.runCatching { Base64.getDecoder().decode(value) }.isSuccess
    }

public fun <T> PropertyValidationContext<T, String?>.isValidBase64OrNull(): PropertyValidationContext<T, String?> =
    isValid(Base64Text) {
        value == null || kotlin.runCatching { Regex("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$") }.isSuccess
    }

public fun <T> PropertyValidationContext<T, out CharSequence>.hasSize(
    min: Int = Int.MIN_VALUE,
    max: Int = Int.MAX_VALUE,
): PropertyValidationContext<T, out CharSequence> =
    isValid(Size("length", min, max)) {
        it.length in min..max
    }

public fun <T> PropertyValidationContext<T, out CharSequence>.matches(
    regex: Regex,
): PropertyValidationContext<T, out CharSequence> =
    isValid(Matches(regex)) {
        regex.matches(it)
    }

public fun <T> PropertyValidationContext<T, out CharSequence?>.matchesOrNull(
    regex: Regex,
): PropertyValidationContext<T, out CharSequence?> =
    isValid(Matches(regex)) {
        it == null || regex.matches(it)
    }

public fun <T> PropertyValidationContext<T, out CharSequence>.doesNotMatch(
    regex: Regex,
): PropertyValidationContext<T, out CharSequence> =
    isValid(NotMatch(regex)) {
        !regex.matches(it)
    }

public fun <T> PropertyValidationContext<T, out CharSequence?>.doesNotMatchOrNull(
    regex: Regex,
): PropertyValidationContext<T, out CharSequence?> =
    isValid(NotMatch(regex)) {
        it == null || !regex.matches(it)
    }

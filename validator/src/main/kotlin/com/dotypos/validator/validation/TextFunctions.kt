package com.dotypos.validator.validation

import com.dotypos.validator.constraint.*
import com.dotypos.validator.constraint.Currency
import com.dotypos.validator.context.PropertyValidationContext
import com.dotypos.validator.isValid
import java.util.*

fun <T> PropertyValidationContext<T, String>.isValidColor(requireLeadingHash: Boolean = true) =
    isValid(HexColor(requireLeadingHash)) {
        if (requireLeadingHash) {
            it.matches(Regex("^#[0-9A-F]{6,8}$"))
        } else {
            it.matches(Regex("^[0-9A-F]{6,8}$"))
        }
    }

fun <T> PropertyValidationContext<T, String>.isValidCurrency() =
    isValid(Currency) {
        it.matches(Regex("^[A-Z]{3}$"))
    }

fun <T> PropertyValidationContext<T, String>.isValidCountry() =
    isValid(Country) {
        it.matches(Regex("^[A-Z]{2}$"))
    }

fun <T> PropertyValidationContext<T, String>.isValidBase64() =
    isValid(Base64Text) {
        kotlin.runCatching { Base64.getDecoder().decode(value) }.isSuccess
    }

fun <T> PropertyValidationContext<T, String?>.isValidBase64OrNull() =
    isValid(Base64Text) {
        value == null || kotlin.runCatching { Regex("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$") }.isSuccess
    }

fun <T> PropertyValidationContext<T, out CharSequence>.hasSize(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE) =
    isValid(Size("length", min, max)) {
        it.length in min..max
    }

fun <T> PropertyValidationContext<T, out CharSequence>.matches(regex: Regex) =
    isValid(Matches(regex)) {
        regex.matches(it)
    }

fun <T> PropertyValidationContext<T, out CharSequence?>.matchesOrNull(regex: Regex) =
    isValid(Matches(regex)) {
        it == null || regex.matches(it)
    }

fun <T> PropertyValidationContext<T, out CharSequence>.doesNotMatch(regex: Regex) =
    isValid(NotMatch(regex)) {
        !regex.matches(it)
    }

fun <T> PropertyValidationContext<T, out CharSequence?>.doesNotMatchOrNull(regex: Regex) =
    isValid(NotMatch(regex)) {
        it == null || !regex.matches(it)
    }

package com.dotypos.validator

import java.util.concurrent.atomic.AtomicBoolean

object ErrorCollector {
    val active: Boolean
        get() = locked.get()

    private val errors = mutableListOf<ValidationError>()
    private val locked = AtomicBoolean(false)

    fun <T> runSafely(action: () -> T): ValidationResult<T> {
        if (!locked.compareAndSet(false, true)) {
            throw IllegalStateException("Another validation is performed")
        }
        val actionResult = action()
        val result = if (errors.isEmpty()) {
            ValidationResult.Success(actionResult)
        } else {
            ValidationResult.Error(errors.toList())
        }
        errors.clear()
        locked.set(false)
        return result
    }

    fun collect(e: ValidationError) {
        synchronized(errors) {
            errors += e
        }
    }

    operator fun plusAssign(e: ValidationError) = collect(e)

    sealed class ValidationResult<T> {
        class Success<T>(val value: T) : ValidationResult<T>()
        class Error<T>(val errors: List<ValidationError>) : ValidationResult<T>()
    }
}

fun <T> withSafeValidation(action: () -> T): ErrorCollector.ValidationResult<T> {
    return ErrorCollector.runSafely(action)
}
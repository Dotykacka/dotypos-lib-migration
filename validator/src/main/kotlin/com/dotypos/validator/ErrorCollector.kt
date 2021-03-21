package com.dotypos.validator

object ErrorCollector {
    var active: Boolean = false

    private val errors = mutableListOf<ValidationError>()

    fun reset() {
        errors.clear()
    }

    fun collect(e: ValidationError) {
        errors += e
    }

    operator fun plusAssign(e: ValidationError) = collect(e)
}
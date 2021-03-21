package com.dotypos.validator.constraint

object EqualsDual : ValidationConstraint() {
    override val description: String
        get() = "not equals"
}

object NotEqualsDual : ValidationConstraint() {
    override val description: String
        get() = "equals"
}
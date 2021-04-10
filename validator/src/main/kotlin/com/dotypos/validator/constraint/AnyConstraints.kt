package com.dotypos.validator.constraint

public object EqualsDual : ValidationConstraint() {
    override val description: String
        get() = "not equals"
}

public object NotEqualsDual : ValidationConstraint() {
    override val description: String
        get() = "equals"
}
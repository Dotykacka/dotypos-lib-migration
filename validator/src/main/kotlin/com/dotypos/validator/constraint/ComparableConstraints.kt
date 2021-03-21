package com.dotypos.validator.constraint

data class Less<T>(val value:T) : ValidationConstraint() {
    override val description = "is not less than $value"
}

data class LessOrEqual<T>(val value:T) : ValidationConstraint() {
    override val description = "is not less or equal to $value"
}

data class Greater<T>(val value:T) : ValidationConstraint() {
    override val description = "is not greater than $value"
}

data class GreaterOrEqual<T>(val value:T) : ValidationConstraint() {
    override val description = "is not greater or equal to $value"
}

data class Between<T>(val start: T, val end: T) : ValidationConstraint() {
    override val description = "is not between $start and $end"
}

data class NotBetween<T>(val start: T, val end: T) : ValidationConstraint() {
    override val description = "is between $start and $end"
}
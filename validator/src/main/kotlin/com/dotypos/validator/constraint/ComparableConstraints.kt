package com.dotypos.validator.constraint

public data class Less<T>(val value:T) : ValidationConstraint() {
    override val description: String = "is not less than $value"
}

public data class LessOrEqual<T>(val value:T) : ValidationConstraint() {
    override val description: String = "is not less or equal to $value"
}

public data class Greater<T>(val value:T) : ValidationConstraint() {
    override val description: String = "is not greater than $value"
}

public data class GreaterOrEqual<T>(val value:T) : ValidationConstraint() {
    override val description: String = "is not greater or equal to $value"
}

public data class Between<T>(val start: T, val end: T) : ValidationConstraint() {
    override val description: String = "is not between $start and $end"
}

public data class NotBetween<T>(val start: T, val end: T) : ValidationConstraint() {
    override val description: String = "is between $start and $end"
}
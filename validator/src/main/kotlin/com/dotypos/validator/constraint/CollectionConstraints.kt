package com.dotypos.validator.constraint

object Empty : ValidationConstraint() {
    override val description = "is not empty"
}

object NotEmpty : ValidationConstraint() {
    override val description = "is empty"
}

object Contains: ValidationConstraint() {
    override val description = "does not contain"
}

object NotContains: ValidationConstraint() {
    override val description = "contains"
}


data class Size(
    val type: String = "size",
    val min: Int = Int.MIN_VALUE,
    val max: Int = Int.MAX_VALUE,
) : ValidationConstraint() {
    override val description: String
        get() {
            return when {
                min != Int.MIN_VALUE && max != Int.MAX_VALUE -> {
                    "$type is not between $min and $max"
                }
                min == Int.MIN_VALUE -> {
                    "$type must be at most $max"
                }
                max == Int.MAX_VALUE -> {
                    "$type must be at least $min"
                }
                // Should not happen
                else -> "$type is not between $min and $max"
            }
        }
}
package com.dotypos.validator.constraint

public object IsUnique : ValidationConstraint() {
    override val description: String = "is not unique"
}
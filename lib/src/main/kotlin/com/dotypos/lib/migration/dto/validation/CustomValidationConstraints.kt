package com.dotypos.lib.migration.dto.validation

import com.dotypos.validator.constraint.ValidationConstraint

open class Relation() : ValidationConstraint() {
    override val description = "no relation to parent found"
}

class RelationToEntity(): Relation() {
    override val description = "no relation to entity found"
}
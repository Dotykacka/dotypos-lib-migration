package com.dotypos.lib.migration.dto.validation

import com.dotypos.validator.ValidationError

class MissingRelatedEntityValidationError(
    val propertyParent: Any?,
    val propertyName: String,
    val keyName: String,
    val relatedPropertyParent: Any?,
    val relatedPropertyName: String,
    val relatedPropertyKeyName: String,
    val values: Iterable<Any>,
    val keys: Iterable<Any>
) : ValidationError(
    message = run {
        val fullPropertyName = "${propertyParent?.javaClass?.simpleName}.$propertyName[].${keyName}"
        val fullRelatedPropertyName =
            "${relatedPropertyParent?.javaClass?.simpleName}.$relatedPropertyName[].$relatedPropertyKeyName"
        "Validation of `$fullPropertyName` failed. " +
                "Reason: No related entity found in `$fullRelatedPropertyName`. " +
                "Missing key(s): $keys"
    }
)
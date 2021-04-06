package com.dotypos.lib.migration.dto.validation

import com.dotypos.validator.ValidationError
import java.math.BigDecimal

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

class UnexpectedSumValue(
    val propertyParent: Any?,
    val itemsPropertyName: String,
    val keyName: String,
    val sumPropertyParent: Any?,
    val sumPropertyName: String,
    val values: Iterable<Any>,
    val calculated: BigDecimal,
    val expected: BigDecimal,
) : ValidationError(
    message = run {
        val fullPropertyName = "${propertyParent?.javaClass?.simpleName}.$itemsPropertyName[]"
        val fullExpectedValueName = "${sumPropertyParent?.javaClass?.simpleName}.$sumPropertyName"
        "Validation of `$fullPropertyName` failed. " +
                "Reason: Summary of `$fullPropertyName.${keyName}` is not equal to value of $fullExpectedValueName. " +
                "Expected value: '$expected', Calculated value: '$calculated' Values: '$values'"
    }
)
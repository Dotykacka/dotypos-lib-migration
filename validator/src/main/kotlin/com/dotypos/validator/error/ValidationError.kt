package com.dotypos.validator

import com.dotypos.validator.constraint.ValidationConstraint

open class ValidationError(message: String) : Exception(message)

class SimpleConstraintValidationError(
    val parent: Any?,
    val propertyName: String,
    val propertyValue: String?,
    val constraint: ValidationConstraint,
    val explanation: String = constraint.description,
) : ValidationError("Validation of `${parent?.javaClass?.simpleName}.$propertyName` failed. Reason: $explanation, Value: '$propertyValue'")

class SimpleDualConstraintValidationError(
    val parent: Any?,
    val property1Name: String,
    val property1Value: String?,
    val property2Name: String,
    val property2Value: String?,
    val constraint: ValidationConstraint,
    val explanation: String = constraint.description,
) : ValidationError("Validation of `${parent?.javaClass?.simpleName}.$property1Name` and `${parent?.javaClass?.simpleName}.$property2Name` failed. Reason: $explanation, Values: '$property1Value', '$property2Value'")

class UniqueConstraintValidationError(
    val parent: Any?,
    val propertyName: String,
    val propertyIndex: Int? = null,
    val childPropertyName: String,
    val childPropertyValue: String?,
    val constraint: ValidationConstraint,
    val explanation: String = constraint.description,
) : ValidationError("\"Validation of `${parent?.javaClass?.simpleName}.$propertyName[${propertyIndex ?: ""}].$childPropertyName` failed. Reason: $explanation, Value: $childPropertyValue")
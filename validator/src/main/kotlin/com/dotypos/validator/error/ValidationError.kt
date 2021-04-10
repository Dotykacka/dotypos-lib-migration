package com.dotypos.validator

import com.dotypos.validator.constraint.ValidationConstraint

public open class ValidationError(message: String) : Exception(message)

public class SimpleConstraintValidationError(
    public val parent: Any?,
    public val propertyName: String,
    public val propertyValue: String?,
    public val constraint: ValidationConstraint,
    public val explanation: String = constraint.description,
) : ValidationError("Validation of `${parent?.javaClass?.simpleName}.$propertyName` failed. Reason: $explanation, Value: '$propertyValue'")

public class SimpleDualConstraintValidationError(
    public val parent: Any?,
    public val property1Name: String,
    public val property1Value: String?,
    public val property2Name: String,
    public val property2Value: String?,
    public val constraint: ValidationConstraint,
    public val explanation: String = constraint.description,
) : ValidationError("Validation of `${parent?.javaClass?.simpleName}.$property1Name` and `${parent?.javaClass?.simpleName}.$property2Name` failed. Reason: $explanation, Values: '$property1Value', '$property2Value'")

public class UniqueConstraintValidationError(
    public val parent: Any?,
    public val propertyName: String,
    public val propertyIndex: Int? = null,
    public val childPropertyName: String,
    public val childPropertyValue: String?,
    public val constraint: ValidationConstraint,
    public val explanation: String = constraint.description,
) : ValidationError("\"Validation of `${parent?.javaClass?.simpleName}.$propertyName[${propertyIndex ?: ""}].$childPropertyName` failed. Reason: $explanation, Value: $childPropertyValue")
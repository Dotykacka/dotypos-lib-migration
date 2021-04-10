package com.dotypos.validator.constraint

public object Blank : ValidationConstraint() {
    override val description: String = "is blank"
}

public object NotBlank : ValidationConstraint() {
    override val description: String = "is not blank"
}

public class HexColor(private val withLeadingHash: Boolean) : ValidationConstraint() {
    override val description: String
        get() = if (withLeadingHash) {
            "is not hex color (with leading '#')"
        } else {
            "is not hex color"
        }
}

public object Currency : ValidationConstraint() {
    override val description: String
        get() = "is not valid currency"
}

public object Country : ValidationConstraint() {
    override val description: String
        get() = "is not valid country"
}

public object Base64Text : ValidationConstraint() {
    override val description: String
        get() = "is not valid base64 encoded string"
}

public class Matches(private val regex: Regex) : ValidationConstraint() {
    override val description: String = "does not match pattern ${regex.pattern}"
}

public class NotMatch(private val regex: Regex) : ValidationConstraint() {
    override val description: String = "matches pattern ${regex.pattern}"
}
package com.dotypos.validator.constraint

object Blank : ValidationConstraint() {
    override val description = "is blank"
}

object NotBlank : ValidationConstraint() {
    override val description = "is not blank"
}

class HexColor(private val withLeadingHash: Boolean) : ValidationConstraint() {
    override val description: String
        get() = if (withLeadingHash) {
            "is not hex color (with leading '#')"
        } else {
            "is not hex color"
        }
}

object Currency : ValidationConstraint() {
    override val description: String
        get() = "is not valid currency"
}

object Country : ValidationConstraint() {
    override val description: String
        get() = "is not valid country"
}

object Base64Text : ValidationConstraint() {
    override val description: String
        get() = "is not valid base64 encoded string"
}

class Matches(val regex: Regex) : ValidationConstraint() {
    override val description = "does not match pattern ${regex.pattern}"
}

class NotMatch(val regex: Regex) : ValidationConstraint() {
    override val description = "matches pattern ${regex.pattern}"
}
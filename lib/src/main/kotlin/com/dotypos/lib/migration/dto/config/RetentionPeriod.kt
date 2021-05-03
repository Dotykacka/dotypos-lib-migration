package com.dotypos.lib.migration.dto.config

data class RetentionPeriod(
    val value: Long,
    val unit: RetentionPeriod.RetentionUnit,
) {

    override fun toString() = "$value${unit.abbreviation}"

    enum class RetentionUnit(val abbreviation: String) {
        DAY(abbreviation = "d"),
        WEEK(abbreviation = "w"),
        MONTH(abbreviation = "m"),
        QUARTER(abbreviation = "q");
    }

    companion object {
        val DEFAULT_RETENTION_PERIOD = RetentionPeriod(value = 2, unit = RetentionUnit.WEEK)

        fun parse(value: String): RetentionPeriod {
            return kotlin.runCatching {
                val periodUnit = RetentionUnit.values().first { value.endsWith(it.abbreviation) }
                RetentionPeriod(
                    value = value.substringBefore(periodUnit.abbreviation).toLong(),
                    unit = periodUnit,
                )
            }.getOrNull() ?: DEFAULT_RETENTION_PERIOD
        }
    }
}

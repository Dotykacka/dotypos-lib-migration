package com.dotypos.lib.migration.dto.validation

import com.dotypos.lib.migration.dto.LONG_SAFE_RANGE
import org.valiktor.Validator
import org.valiktor.functions.isBetween

fun Validator<*>.Property<Long?>.isValidId() =
    this.isBetween(LONG_SAFE_RANGE.first, LONG_SAFE_RANGE.last)
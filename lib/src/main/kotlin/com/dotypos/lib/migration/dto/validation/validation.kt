package com.dotypos.lib.migration.dto.validation

import com.dotypos.lib.migration.dto.LONG_SAFE_RANGE
import kotlin.reflect.KProperty1

fun <T> T.isValidId(property: KProperty1<T, Long>) =
    require(property.get(this) in LONG_SAFE_RANGE) {
        "${property.name} is not valid id"
    }
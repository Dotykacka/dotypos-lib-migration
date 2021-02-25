package com.dotypos.lib.migration.dto.validation

import com.dotypos.lib.migration.dto.LONG_SAFE_RANGE
import com.dotypos.lib.migration.dto.entity.BaseEntityDto
import org.valiktor.Validator
import org.valiktor.functions.isBetween
import org.valiktor.functions.isValid

fun Validator<*>.Property<Long?>.isValidId() =
    this.isBetween(LONG_SAFE_RANGE.first, LONG_SAFE_RANGE.last)

fun <T : BaseEntityDto> Validator<*>.Property<Iterable<T>?>.hasUniqueItemIds() =
    this.isValid {
        val idList = it.map { it.id }
        idList.size == idList.toSet().size
    }

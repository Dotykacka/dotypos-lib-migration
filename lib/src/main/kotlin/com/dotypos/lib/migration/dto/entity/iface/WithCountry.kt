package com.dotypos.lib.migration.dto.entity.iface

interface WithCountry {
    /**
     * [ISO 3166 alpha2](https://www.iso.org/obp/ui/#search/code/) code of country
     */
    val country: String

    companion object {
        const val SERIALIZED_NAME = "country"
        const val DEFAULT_COUNTRY = "CZ"
    }
}
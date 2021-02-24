package com.dotypos.lib.migration.demo

import com.dotypos.lib.migration.demo.creator.DataCreator
import com.dotypos.lib.migration.demo.creator.EmptyDemoDataCreator
import com.dotypos.lib.migration.demo.creator.DynamicDataCreator

enum class DemoExportType(val id: String, val creator: DataCreator) {
    EMPTY("empty", creator = EmptyDemoDataCreator),
    RESTAURANT_SMALL("restaurant-small", creator = DynamicDataCreator(
        seed = 0,
        employees = 80,
        products = 300,
        ingredients = 200,
        categories = 15,
        customers = 100,
        courses = 5,
        discountGroups = 5,
        tablePages = 2,
        tables = 40,
        warehouses = 1,
        suppliers = 20,
        printers = 1,
        documents = 5_000,
    )),
    RESTAURANT("restaurant", creator = DynamicDataCreator(
        seed = 0,
        products = 300,
        categories = 15,
        employees = 80,
        customers = 2000,
        courses = 5,
        discountGroups = 5,
        tablePages = 2,
        tables = 40,
    )),
    LARGE_V1("large.v1", creator = DynamicDataCreator(
        seed = 0,
        products = 20_000,
    )),
}
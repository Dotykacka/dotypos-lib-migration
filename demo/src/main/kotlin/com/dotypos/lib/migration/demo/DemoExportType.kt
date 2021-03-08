package com.dotypos.lib.migration.demo

import com.dotypos.lib.migration.demo.creator.DataCreator
import com.dotypos.lib.migration.demo.creator.DynamicDataCreator
import com.dotypos.lib.migration.demo.creator.EmptyDemoDataCreator

enum class DemoExportType(val id: String, val makeCreator: () -> DataCreator) {
    EMPTY("empty", makeCreator = { EmptyDemoDataCreator }),
    RESTAURANT_SMALL("restaurant-small", makeCreator = {
        DynamicDataCreator(
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
        )
    }),
    RESTAURANT(
        "restaurant",
        makeCreator = {
            DynamicDataCreator(
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
                documents = 70_000,
            )
        },
    ),
    ESHOP(
        "eshop", makeCreator = {
            DynamicDataCreator(
                seed = 0,
                categories = 300,
                products = 50_000,
                employees = 20,
                ingredients = 0,
                customers = 15_000,
                discountGroups = 3,
                courses = 0,
                tablePages = 0,
                tables = 0,
                warehouses = 1,
                suppliers = 100,
                printers = 1,
                documents = 20_000,
            )
        }
    )
}
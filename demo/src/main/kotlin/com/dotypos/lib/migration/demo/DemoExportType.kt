package com.dotypos.lib.migration.demo

import com.dotypos.lib.migration.demo.creator.DataCreator
import com.dotypos.lib.migration.demo.creator.EmptyDemoDataCreator

enum class DemoExportType(val id: String, val creator: DataCreator) {
    EMPTY("empty", creator = EmptyDemoDataCreator),
}
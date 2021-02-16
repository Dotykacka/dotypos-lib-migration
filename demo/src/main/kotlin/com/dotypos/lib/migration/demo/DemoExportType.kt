package com.dotypos.lib.migration.demo

import com.dotypos.lib.migration.demo.creator.DataCreator
import com.dotypos.lib.migration.demo.creator.EmptyDemoDataCreator
import com.dotypos.lib.migration.demo.creator.LargeDemoDataCreatorV1

enum class DemoExportType(val id: String, val creator: DataCreator) {
    EMPTY("empty", creator = EmptyDemoDataCreator),
    LARGE_V1("large.v1", creator = LargeDemoDataCreatorV1(seed = 0))
}
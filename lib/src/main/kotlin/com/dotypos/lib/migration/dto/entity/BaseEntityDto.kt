package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithVersion

abstract class BaseEntityDto : WithId, WithVersion
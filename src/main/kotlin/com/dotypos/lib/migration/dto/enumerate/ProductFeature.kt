package com.dotypos.lib.migration.dto.enumerate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ProductFeature {
    /**
     * Product requires price entry before adding to order.
     */
    @SerialName("requirePriceEntry")
    REQUIRE_PRICE_ENTRY,

    /**
     * Product requires quantity before adding to order.
     */
    @SerialName("requireQuantityEntry")
    REQUIRE_QUANTITY_ENTRY,

    /**
     * Product represents service billed by time.
     * `measurementUnit` of product must be in [MigrationMeasurementUnit.Group.TIME] group.
     */
    @SerialName("withTimeMeasurement")
    WITH_TIME_MEASUREMENT,

    /**
     * All ingredients of product will be added with product to order. )
     * No stock deducting of item will be performed during the order issue.
     */
    @SerialName("jointSale")
    JOINT_SALE,

    /**
     * Product will not fiscalized (currently working for CZ fiscalization only).
     */
    @SerialName("fiscalizationDisabled")
    FISCALIZATION_DISABLED,

    /**
     * Product is assembled. Product ingredients won't be deducted directly by sale,
     * but product can be made in Stock App.
     */
    @SerialName("assembled")
    ASSEMBLED,

    /**
     * Regarding to takeaway behavior enables ability to set product as sold to take away.
     */
    @SerialName("takeaway")
    TAKEAWAY,
}
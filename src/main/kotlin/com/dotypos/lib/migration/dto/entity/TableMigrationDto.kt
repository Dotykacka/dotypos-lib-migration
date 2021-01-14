package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Table displayed on Table map
 */
@Serializable
data class TableMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(SellerRelated.SERIAL_NAME)
    override val sellerId: Long?,

    /**
     * ID of parent [TablePageMigrationDto]
     */
    @SerialName("tablePageId")
    val tablePageId: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    @SerialName("type")
    val type: TableType,

    /**
     * Number of seats available on table (used for reservations)
     */
    @SerialName("seats")
    val seats: Int,

    /**
     * Horizontal position of table on map
     */
    @SerialName("positionX")
    val positionX: Int,

    /**
     * Vertical position on table on map
     */
    @SerialName("positionY")
    val positionY: Int,

    /**
     * Rotation of table on map, supported values: 0 90
     */
    @SerialName("rotation")
    val rotation: Int,

    @SerialName(WithTags.SERIAL_NAME)
    override val tags: List<String>,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName, Deletable, SellerRelated, WithTags {

    /**
     * List of available table types
     * width and height are used for validation of tables overlapping
     */
    @Serializable
    enum class TableType(
        val width: Int,
        val height: Int,
        val defaultSeats: Int
    ) {
        /**
         * Square table by default with 4 seats
         */
        @SerialName("square")
        SQUARE(width = 68, height = 68, defaultSeats = 4),

        /**
         * Square table by default with 6 seats
         */
        @SerialName("rectangle")
        RECTANGLE(width = 104, height = 68, defaultSeats = 6),

        /**
         * Square table by default with 2 seats
         */
        @SerialName("circle2")
        CIRCLE2(width = 68, height = 68, defaultSeats = 2),

        /**
         * Square table by default with 4 seats
         */
        @SerialName("circle4")
        CIRCLE4(width = 68, height = 46, defaultSeats = 4),

        /**
         * Special table for delivery, used for reporting, not used for reservations
         */
        @SerialName("delivery")
        DELIVERY(width = 104, height = 68, defaultSeats = 0),
    }
}
package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.Deletable
import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithVersion
import com.dotypos.lib.migration.dto.enumerate.PrintTaskType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrintTaskMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * Type of print task
     */
    @SerialName("type")
    val type: PrintTaskType,

    /**
     * Locale to be used for printouts. [DEFAULT_PRINT_LOCALE] to use current locale of device.
     */
    @SerialName("locale")
    val locale: String = DEFAULT_PRINT_LOCALE,

    /**
     * Header of printout - used only for [PrintTaskType.RECEIPT] and [PrintTaskType.INVOICE]
     */
    @SerialName("header")
    val header: String?,

    /**
     * Number of copies to be printed
     */
    @SerialName("copies")
    val copies: Int = 1,

    /**
     * Beep after printout (default for []]
     */
    @SerialName("beep")
    val beep: Boolean,

    /**
     * Use secondary font as default for this task
     */
    @SerialName("useFontB")
    val useFontB: Boolean = false,

    /**
     * List of filters applied to printout - only [TagItemPrintFilter] supported for migration now.
     */
    @SerialName("filters")
    val filters: List<TagItemPrintFilter>,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long
) : BaseEntityDto() {

    companion object {
        const val DEFAULT_PRINT_LOCALE = "default"
    }

    @Serializable
    data class TagItemPrintFilter(
        /**
         * Tags of items to be included/excluded depending on value of [include].
         */
        @SerialName("tags")
        val tags: Set<String>,

        /**
         * Sets inclusive/exclusive behavior of the filter.
         */
        @SerialName("include")
        val include: Boolean = true
    )
}

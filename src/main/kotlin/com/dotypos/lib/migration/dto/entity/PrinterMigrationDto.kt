package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.Deletable
import com.dotypos.lib.migration.dto.entity.iface.WithId
import com.dotypos.lib.migration.dto.entity.iface.WithName
import com.dotypos.lib.migration.dto.entity.iface.WithVersion
import com.dotypos.lib.migration.dto.enumerate.PrinterConnectionMode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrinterMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    /**
     * Connection mode of printer.
     */
    @SerialName("connectionMode")
    val connectionMode: PrinterConnectionMode,

    /**
     * Printer address in supported format depending on [connectionMode]:
     * - [PrinterConnectionMode.USB]: `vendorId:productId` of connected printer
     * - [PrinterConnectionMode.NETWORK]: IP address with optional port (default is 9100)
     * - [PrinterConnectionMode.BLUETOOTH]: MAC address
     */
    @SerialName("address")
    val address: String,

    /**
     * Encoding supported by printer
     */
    @SerialName("encoding")
    val encoding: String = DEFAULT_ENCODING,

    /**
     * Number of characters to be used when printing with Font A
     */
    @SerialName("charactersFontA")
    val charactersFontA: Int,

    /**
     * Number of characters to be used when printing with Font B
     */
    @SerialName("charactersFontB")
    val charactersFontB: Int,

    /**
     * Number of lines to append after printouts.
     */
    @SerialName("appendLines")
    val appendLines: Int,

    /**
     * Beep supported.
     */
    @SerialName("beep")
    val canBeep: Boolean,

    /**
     * Cut supported
     */
    @SerialName("cut")
    val canCut: Boolean,

    /**
     * Can be cash drawer connected
     */
    @SerialName("drawer")
    val withDrawer: Boolean,

    /**
     * List of print tasks of the printer
     */
    @SerialName("tasks")
    val tasks: List<PrintTaskMigrationDto>,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName, Deletable {
    companion object {
        const val DEFAULT_ENCODING = "cp852"
        const val DEFAULT_PRINT_LOCALE = "default"

        /**
         * Creates predefined migration for SunmiV1 internal printer
         * @param id ID of printer
         * @param name Name of printer (Internal printer in customers language)
         * @param tasks List of print tasks
         * @param version version of entity
         */
        fun createSunmiV1InternalPrinter(
            id: Long,
            name: String,
            tasks: List<PrintTaskMigrationDto>,
            version: Long
        ) = PrinterMigrationDto(
            id = id,
            name = name,
            connectionMode = PrinterConnectionMode.INTERNAL_SUNMI_V1,
            address = "local",
            encoding = Charsets.UTF_8.name(),
            charactersFontA = 32,
            charactersFontB = 38,
            appendLines = 4,
            canBeep = false,
            canCut = false,
            withDrawer = false,
            tasks = tasks,
            isDeleted = false,
            version = version
        )

        /**
         * Creates predefined migration for SunmiV2 internal printer
         * @param id ID of printer
         * @param name Name of printer (Internal printer in customers language)
         * @param tasks List of print tasks
         * @param version version of entity
         */
        fun createSunmiV2InternalPrinter(
            id: Long,
            name: String,
            tasks: List<PrintTaskMigrationDto>,
            version: Long
        ) = PrinterMigrationDto(
            id = id,
            name = name,
            connectionMode = PrinterConnectionMode.INTERNAL_SUNMI_V2,
            address = "local",
            encoding = Charsets.UTF_8.name(),
            charactersFontA = 32,
            charactersFontB = 38,
            appendLines = 4,
            canBeep = false,
            canCut = false,
            withDrawer = false,
            tasks = tasks,
            isDeleted = false,
            version = version
        )

        /**
         * Creates predefined migration for Landi A8 internal printer
         * @param id ID of printer
         * @param name Name of printer (Internal printer in customers language)
         * @param tasks List of print tasks
         * @param version version of entity
         */
        fun createLandiA8InternalPrinter(
            id: Long,
            name: String,
            tasks: List<PrintTaskMigrationDto>,
            version: Long
        ) = PrinterMigrationDto(
            id = id,
            name = name,
            connectionMode = PrinterConnectionMode.INTERNAL_LANDI_A8,
            address = "local",
            encoding = Charsets.UTF_8.name(),
            charactersFontA = 32,
            charactersFontB = 38,
            appendLines = 2,
            canBeep = false,
            canCut = false,
            withDrawer = false,
            tasks = tasks,
            isDeleted = false,
            version = version
        )
    }
}

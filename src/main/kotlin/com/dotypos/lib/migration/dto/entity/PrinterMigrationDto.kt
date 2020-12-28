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
     * Locale to be used for printouts. [DEFAULT_PRINT_LOCALE] to use current locale of device.
     */
    @SerialName("locale")
    val locale: String = DEFAULT_PRINT_LOCALE,

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

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), WithName, Deletable {
    companion object {
        const val DEFAULT_ENCODING = "cp852"
        const val DEFAULT_PRINT_LOCALE = "default"
    }
}

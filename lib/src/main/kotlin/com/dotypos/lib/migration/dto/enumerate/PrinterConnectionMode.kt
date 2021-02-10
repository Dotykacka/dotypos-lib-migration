package com.dotypos.lib.migration.dto.enumerate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PrinterConnectionMode {
    @SerialName("usb")
    USB,

    @SerialName("network")
    NETWORK,

    @SerialName("bluetooth")
    BLUETOOTH,

    /**
     * Not implemented in Dotypos yet
     */
    @SerialName("sunmiV1")
    INTERNAL_SUNMI_V1,

    @SerialName("sunmiV2")
    INTERNAL_SUNMI_V2,

    @SerialName("landiA8")
    INTERNAL_LANDI_A8,
}
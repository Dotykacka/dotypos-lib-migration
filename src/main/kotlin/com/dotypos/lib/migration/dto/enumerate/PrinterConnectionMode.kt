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
    BLUETOOTH
}
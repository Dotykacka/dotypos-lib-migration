@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.*
import com.dotypos.lib.migration.dto.enumerate.permission.EmployeeMobileWaiterPermission
import com.dotypos.lib.migration.dto.enumerate.permission.EmployeePosPermission
import com.dotypos.lib.migration.dto.enumerate.permission.EmployeeStockPermission
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
data class EmployeeMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    @SerialName(WithEmail.SERIALIZED_NAME)
    override val email: String?,

    @SerialName(WithColor.SERIALIZED_NAME)
    override val hexColor: String,

    @SerialName(WithPhone.SERIALIZED_NAME)
    override val phone: String?,

    @SerialName(WithTags.SERIAL_NAME)
    override val tags: List<String>,

    @SerialName(WithMaxDiscount.SERIALIZED_NAME)
    override val maxDiscount: BigDecimal?,

    @SerialName(SellerRelated.SERIAL_NAME)
    override val sellerId: Long?,

    /**
     * PIN data wrapper
     */
    @SerialName("pin")
    val pin: PinWrapper?,

    /**
     * Is PIN required for login
     */
    @SerialName("pinRequired")
    val isPinRequired: Boolean,

    /**
     * Set of allowed POS Permissions
     */
    @SerialName("posPermissions")
    val posPermissions: Set<EmployeePosPermission>,

    /**
     * Set of allowed Stock Permissions (the [EmployeePosPermission.STOCK_MANAGEMENT]
     * presented in [posPermissions] is required to gain access to Stock Management)
     */
    @SerialName("stockPermissions")
    val stockPermissions: Set<EmployeeStockPermission>,

    /**
     * Set of Mobile Waiter permissions
     */
    @SerialName("mobileWaiterPermissions")
    val mobileWaiterPermissions: Set<EmployeeMobileWaiterPermission>,

    @SerialName(Enablable.SERIAL_NAME)
    override val isEnabled: Boolean,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(),
    WithName,
    WithEmail,
    WithPhone,
    WithColor,
    WithTags,
    WithMaxDiscount,
    SellerRelated,
    Enablable,
    Deletable {

    @Serializable
    data class PinWrapper(
        /**
         * Type of password
         */
        @SerialName("type")
        val type: Type,

        /**
         * Raw password data
         */
        @SerialName("data")
        val data: String
    ) {
        @Serializable
        enum class Type {
            @SerialName("raw")
            PLAINTEXT,

            @SerialName("markeeta")
            MARKEETA_HASH
        }
    }
}
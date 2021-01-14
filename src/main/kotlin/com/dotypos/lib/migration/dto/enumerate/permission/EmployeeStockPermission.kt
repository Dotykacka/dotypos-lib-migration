package com.dotypos.lib.migration.dto.enumerate.permission

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EmployeeStockPermission() {
    /**
     * Enables access to the Stock Management App (also [EmployeePosPermission.STOCK_MANAGEMENT] required to be set for user).
     */
    @SerialName("stockView")
    STOCK_VIEW,

    /**
     * Enables to make stock-up.
     */
    @SerialName("stockStore")
    STOCK_STORE,

    /**
     * Enables to make stock corrections.
     */
    @SerialName("storeCorrections")
    STOCK_CORRECTIONS,

    /**
     * Enables to modify purchase price when stock-up.
     */
    @SerialName("stockStoreEnterPrice")
    STOCK_STORE_ENTER_PRICE,

    /**
     * Enables to display current stock status.
     */
    @SerialName("stockShowOnStockAmount")
    STOCK_SHOW_ON_STOCK_AMOUNT,

    /**
     * Enables to display Inventory report.
     */
    @SerialName("inventoryView")
    INVENTORY_VIEW,

    /**
     * Enables to create new Inventory.
     */
    @SerialName("inventoryCreate")
    INVENTORY_CREATE,

    /**
     * Enables to display amount of current stock status during the inventory.
     */
    @SerialName("inventoryShowOnStockAmount")
    INVENTORY_SHOW_ON_STOCK_AMOUNT,

    /**
     * Enables to display the Warehouse management and to rename warehouses.
     */
    @SerialName("warehouseEdit")
    WAREHOUSE_EDIT,

    /**
     * Enables to display the Reporting.
     */
    @SerialName("reportView")
    REPORT_VIEW,

    /**
     * Enables to export reports and send them by email.
     */
    @SerialName("reportEdit")
    REPORT_EDIT,

    /**
     * Enables to display the Supplier management.
     */
    @SerialName("supplierView")
    SUPPLIER_VIEW,

    /**
     * Enables to create new and edit existing suppliers.
     */
    @SerialName("supplierEdit")
    SUPPLIER_EDIT,
}
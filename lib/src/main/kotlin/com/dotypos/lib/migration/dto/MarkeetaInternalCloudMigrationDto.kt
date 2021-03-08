package com.dotypos.lib.migration.dto

import com.dotypos.lib.migration.dto.entity.markeeta.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Company / Merchant billing data.
 */
@Serializable
data class MarkeetaInternalCloudMigrationDto(

    /**
     * Contact name.
     */
    @SerialName("contactName")
    val contanctName: String,

    /**
     * Company identification - like ico in CZ.
     */
    @SerialName("companyId")
    val companyId: String,

    /**
     * Vat company identification - like dic in CZ.
     */
    @SerialName("vatId")
    val vatId: String,

    /**
     * Additional info about company.
     */
    @SerialName("note")
    val note: String?,

    /**
     * List of persons, that could be invoiced.
     */
    @SerialName("people")
    val people: List<PersonMarkeetaMigrationDto>,

    /**
     * List of all addresses for company - including addresses of shop, merchant, correspondence etc.
     */
    @SerialName("addresses")
    val addresses: List<AddressMarkeetaMigrationDto>,

    /**
     * Government public data about company e.g. https://wwwinfo.mfcr.cz/ares/ares_es.html.cz
     */
    @SerialName("registers")
    val registers: List<RegisterMarkeetaMigrationDto>,

    /**
     * List of all company orders.
     */
    @SerialName("orders")
    val orders: List<OrderMarkeetaMigrationDto>,

    /**
     * List of all licenses, the company uses.
     */
    @SerialName("licenses")
    val licenses: List<LicenseMarkeetaMigrationDto>,

    /**
     * List of all other services, the company uses e.g. sim cards.
     */
    @SerialName("services")
    val services: List<ServiceMarkeetaMigrationDto>,

    /**
     * Data needed for billing.
     */
    @SerialName("billingInformations")
    val billingInformations: List<BillingInformationMarkeetaMigrationDto>,

    /**
     * Billing documents.
     */
    @SerialName("documents")
    val documents: List<DocumentMarkeetaMigrationDto>,
)
package com.dotypos.lib.migration.dto

import com.dotypos.lib.migration.dto.entity.suc.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Company / Merchant billing data.
 */
@Serializable
data class SucCloudMigrationDto(

    /**
     * Company name.
     */
    @SerialName("nazev_kontaktu")
    val contanctName: String,

    @SerialName("ico")
    val companyId: String,

    @SerialName("dic")
    val companyId2: String,

    @SerialName("poznamka")
    val note: String?,

    /**
     * List of persons, that could be invoiced.
     */
    @SerialName("person")
    val personList: List<PersonDto>,

    /**
     * List of all addresses for company - including addresses of shop, merchant, correspondence etc.
     */
    @SerialName("addresses")
    val addressList: List<AddressDto>,

    /**
     * Government public data about company e.g. https://wwwinfo.mfcr.cz/ares/ares_es.html.cz
     */
    @SerialName("register")
    val registerDataList: List<RegisterDto>,

    /**
     * List of all company orders.
     */
    @SerialName("objednavka")
    val orderList: List<OrderDto>,

    /**
     * List of all licences, the company uses.
     */
    @SerialName("licence")
    val licenceList: List<LicenceDto>,

    /**
     * List of all other services, the company uses e.g. sim cards.
     */
    @SerialName("sluzby")
    val serviceList: List<ServiceDto>,

    /**
     * Data needed for billing.
     */
    @SerialName("fakturacni_udaje")
    val billingInformationList: List<BillingInformationDto>,

    /**
     * Billing documents.
     */
    @SerialName("dokumenty")
    val sucDocumentList: List<SucDocumentDto>

)
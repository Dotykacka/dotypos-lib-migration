@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.CzFiscalizationConstraints
import com.dotypos.lib.migration.dto.entity.DocumentMigrationDto.CancellationType
import com.dotypos.lib.migration.dto.entity.iface.*
import com.dotypos.lib.migration.dto.validation.*
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import com.dotypos.validator.validation.*
import com.dotypos.validator.validationOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal
import java.util.*

/**
 * Issued document
 */
@Serializable
data class DocumentMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * Type of document. Some document conditions are set regarding the type
     */
    @SerialName("type")
    val type: Type,

    /**
     * Type of cancellation of document
     * [CancellationType.NONE] if document is not cancelled
     */
    @SerialName("cancellationType")
    val cancellationType: CancellationType = CancellationType.NONE,

    /**
     * ID of related document, required for cancellation documents
     */
    @SerialName("relatedDocumentId")
    val relatedDocumentId: Long?,

    /**
     * ID of [table][TableMigrationDto] to which was document issued
     */
    @SerialName("tableId")
    val tableId: Long?,

    /**
     * Date when was document created (not printed on receipt)
     */
    @SerialName("created")
    val created: Date,

    /**
     * Number of document - printed on receipt/invoice
     */
    @SerialName("documentNumber")
    val documentNumber: String,

    /**
     * Legal date of document issue, used also for fiscalization purposes as Date and time of sale
     */
    @SerialName("issueDate")
    val issueDate: Date,

    /**
     * ID of [customer][CustomerMigrationDto] - printed on receipt/invoice
     */
    @SerialName("customerId")
    val customerId: Long?,

    /**
     * ID of [employee][EmployeeMigrationDto] which issued the document
     */
    @SerialName("employeeId")
    val employeeId: Long?,

    /**
     * Last acquired location info.
     * Can be used for traveling sellers or for regulatory checks (distance from school for betting offices)
     */
    @SerialName("location")
    val location: Location?,

    /**
     * Note printed to document
     */
    @SerialName("note")
    val note: String,

    /**
     * List of items presented on document
     */
    @SerialName("items")
    val items: List<DocumentItemMigrationDto>,

    /**
     * Total value of document including all taxes (to pay)
     */
    @SerialName("totalValue")
    val totalValue: BigDecimal,

    @SerialName(WithCurrency.SERIALIZED_NAME)
    override val currency: String,

    /**
     * Foreign currency info - if presented the document price is also shown in foreign currency
     * Used for printing/reporting
     */
    @SerialName("foreignCurrency")
    val foreignCurrency: ForeignCurrency?,

    /**
     * Marks whether the document is fully paid = summary of money transactions related to document
     * is equal to its [totalValue]
     */
    @SerialName("paid")
    val isPaid: Boolean,

    /**
     * Amount of points acquired by this document
     */
    @SerialName("points")
    val points: BigDecimal,

    /**
     * Was the issuer of document payer of the tax at the moment of issuing
     */
    @SerialName("issuedByVatPayer")
    val issuedByVatPayer: Boolean,

    /**
     * Data related to CZ fiscalization (etrzby;EET)
     */
    @SerialName("czFiscalizationData")
    val czFiscalizationData: CzFiscalizationData?,

    /**
     * ID of of [subject][OnBehalfSaleSubjectMigrationDto] to behalf of is this document issued
     */
    @SerialName("onBehalfSaleSubjectId")
    val onBehalfSaleSubjectId: Long?,

    /**
     * External ID of document - used for pairing with external delivery systems (Uber Eats, Vyzvedni si)
     * Reserved prefixes: uber-eats, vyzvednisi
     */
    @SerialName("externalId")
    val externalId: String?,

    @SerialName(SellerRelated.SERIAL_NAME)
    override val sellerId: Long?,

    @SerialName(WithTags.SERIAL_NAME)
    override val tags: List<String>,

    /**
     * Set true if order should be treated as delivery - used for reporting
     */
    @SerialName("delivery")
    val isDelivery: Boolean,

    /**
     * Document is issued in reverse charge mode - [issuedByVatPayer] must be `true`
     */
    @SerialName("reverseCharge")
    val isReverseCharge: Boolean,

    /**
     * In some countries, the lunch invitation form must be printed on the receipt when used as an accounting expense.
     */
    @SerialName("printWithLunchInvitation")
    val printWithLunchInvitation: Boolean,

    /**
     * Document was created with connected certified scale, it changes behavior of printouts and
     * behavior of customer display to meet legal requirements.
     */
    @SerialName("welmecMode")
    val welmecMode: Boolean,

    /**
     * Plaintext of additional print data - used for printing receipt from payment terminal. New lines separated by `\n`
     */
    @SerialName("printData")
    val printData: String,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long,
) : BaseEntityDto(), SellerRelated, WithTags, WithCurrency {

    init {
        validateId()

        validationOf(DocumentMigrationDto::relatedDocumentId) {
            isValidIdOrNull()
        }

        validationOf(DocumentMigrationDto::tableId) {
            isValidIdOrNull()
        }

        validationOf(DocumentMigrationDto::customerId) {
            isValidIdOrNull()
        }

        validationOf(DocumentMigrationDto::employeeId) {
            isValidIdOrNull()
        }

        validationOf(DocumentMigrationDto::note) {
            hasSize(max = 1000)
        }

        validationOf(DocumentMigrationDto::items) {
            expectSum(
                key = DocumentItemMigrationDto::totalPriceWithVat,
                sumValue = DocumentMigrationDto::totalValue,
            )
        }

        validateCurrency()

        validateSellerId()

        validateVersion()
    }

    /**
     * Type of document
     */
    @Serializable
    enum class Type(
        val isInvoice: Boolean,
        val isCancellation: Boolean,
    ) {
        /**
         * Receipt
         */
        @SerialName("receipt")
        RECEIPT(isInvoice = false, isCancellation = false),

        /**
         * Cancellation of receipt
         */
        @SerialName("receiptCancellation")
        RECEIPT_CANCELLATION(isInvoice = false, isCancellation = true),

        /**
         * Invoice
         */
        @SerialName("invoice")
        INVOICE(isInvoice = true, isCancellation = false),

        /**
         * Cancellation of invoice
         */
        @SerialName("invoiceCancellation")
        INVOICE_CANCELLATION(isInvoice = true, isCancellation = false),

        /**
         * Write-Off of damaged or expired goods.
         */
        @SerialName("writeOff")
        WRITE_OFF(isInvoice = false, isCancellation = false),
    }

    @Serializable
    enum class CancellationType {
        /**
         * Document is not cancelled
         */
        @SerialName("none")
        NONE,

        /**
         * Part of document is cancelled by one or more cancellation documents
         */
        @SerialName("part")
        PART,

        /**
         * Document is fully cancelled by one ore more cancellation documents
         */
        @SerialName("full")
        FULL,
    }

    /**
     * Document location data
     */
    @Serializable
    data class Location(
        /**
         * Date of location info update
         */
        @SerialName("date")
        val date: Date,

        /**
         * Latitude of acquired location
         */
        @SerialName("latitude")
        val latitude: Double,

        /**
         * Longitude of acquired location
         */
        @SerialName("longitude")
        val longitude: Double,

        /**
         * Accuracy in meters of acquired location
         */
        @SerialName("accuracy")
        val accuracy: Float,
    ) {
        init {
            validationOf(Location::latitude)
                .isBetween(start = -90.0, end = 90.0)

            validationOf(Location::longitude)
                .isBetween(start = -180.0, end = 180.0)

            validationOf(Location::accuracy)
                .isGreaterThanOrEqualTo(value = 0.0f)
        }
    }

    @Serializable
    data class ForeignCurrency(
        /**
         * Currency code [ISO 4217 format](https://www.iso.org/iso-4217-currency-codes.html)
         */
        @SerialName("code")
        val code: String,

        /**
         * Exchange rate to new currency.
         * 250 CZK (primary) ~ 10 USD -> 0.04
         * 10 USD (primary) ~ 250 CZK -> 25
         */
        @SerialName("exchangeRate")
        val exchangeRate: BigDecimal,
    )

    @Serializable
    data class CzFiscalizationData(
        /**
         * Business premises ID (id_provoz)
         */
        @SerialName("businessPremissesId")
        val businessPremissesId: String,

        /**
         * Cash register ID (id_pokl)
         */
        @SerialName("cashRegisterId")
        val cashRegisterId: String,

        /**
         * Sale regime (rezim)
         */
        @SerialName("saleRegime")
        val saleRegime: SaleRegime,

        /**
         * Fiscal Identification Code, at least on of [fik]/[bkp] must be presented
         */
        @SerialName("fik")
        val fik: String?,

        /**
         * Taxpayer's Signature Code (pkp)
         */
        @SerialName("pkp")
        val pkp: String,

        /**
         * Taxpayer's Security Code (bkp), at least on of [fik]/[bkp] must be presented
         */
        @SerialName("bkp")
        val bkp: String?,
    ) {
        init {
            validationOf(CzFiscalizationData::businessPremissesId)
                .matches(CzFiscalizationConstraints.BUSINESS_PREMISES_ID_FORMAT)

            validationOf(CzFiscalizationData::cashRegisterId)
                .matches(CzFiscalizationConstraints.CASH_REGISTER_ID_FORMAT)

            validationOf(CzFiscalizationData::fik)
                .matchesOrNull(CzFiscalizationConstraints.FIK_FORMAT)

            validationOf(CzFiscalizationData::bkp)
                .matchesOrNull(CzFiscalizationConstraints.BKP_FORMAT)
        }

        /**
         * Sale regime for Electronic Registration of Sales
         */
        @Serializable
        enum class SaleRegime {
            @SerialName("regular")
            REGULAR,

            @SerialName("simplified")
            SIMPLIFIED
        }
    }
}
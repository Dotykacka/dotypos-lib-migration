@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto.entity

import com.dotypos.lib.migration.dto.entity.iface.*
import com.dotypos.lib.migration.dto.enumerate.MigrationMeasurementUnit
import com.dotypos.lib.migration.dto.enumerate.feature.ProductFeature
import com.dotypos.lib.migration.dto.enumerate.ProductStockOverdraftBehavior
import com.dotypos.lib.migration.dto.validation.isValidId
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isPositiveOrZero
import org.valiktor.validate
import java.math.BigDecimal

@Serializable
data class ProductMigrationDto(
    @SerialName(WithId.SERIAL_NAME)
    override val id: Long,

    /**
     * ID of parent [category][CategoryMigrationDto].
     */
    @SerialName("categoryId")
    val categoryId: Long,

    @SerialName(WithName.SERIAL_NAME)
    override val name: String,

    @SerialName(WithCustomPrintName.SERIAL_NAME)
    override val printName: String? = null,

    /**
     * Product subtitle. Printed on various documents (kitchen print, receipt, invoice,...).
     */
    @SerialName("subtitle")
    val subtitle: String,

    /**
     * Product note. It is added to order item and can be modified on order item by cashier.
     * Printed on multiple documents.
     */
    @SerialName("note")
    val note: String,

    /**
     * List of quick notes available for the product. It is used, for instance,
     * to differentiate variants (rare, medium, well done).
     */
    @SerialName("quickNotes")
    val quickNotes: List<String>,

    /**
     * Description of product. Can be used by external apps, but not used directly in POS.
     */
    @SerialName("description")
    val description: String,

    @SerialName(WithColor.SERIALIZED_NAME)
    override val hexColor: String,

    /**
     * Product packaging affects the number of items added to the account and the modification of the item on the account using the +/- buttons.
     * It is always added and removed in steps of package size.
     */
    @SerialName("packaging")
    val packaging: BigDecimal,

    @SerialName(WithMeasurementUnit.SERIAL_NAME)
    override val measurementUnit: MigrationMeasurementUnit,

    /**
     * Used for price tags printing. Example described in [ComparableMeasurement].
     */
    @SerialName("comparableMeasurement")
    val comparableMeasurement: ComparableMeasurement?,

    /**
     * List of supported [EAN/UPC](https://www.gs1.org/standards/barcodes/ean-upc) codes
     */
    @SerialName("ean")
    val ean: List<String>,

    /**
     * List of supported product lookup codes
     */
    @SerialName("plu")
    val plu: List<String>,

    /**
     * Unit price with VAT
     */
    @SerialName("unitPriceWithVat")
    val unitPriceWithVat: BigDecimal,

    /**
     * Unit price with VAT (group B)
     */
    @SerialName("unitPriceWithVatB")
    val unitPriceWithVatB: BigDecimal? = null,

    /**
     * Unit price with VAT (group C)
     */
    @SerialName("unitPriceWithVatC")
    val unitPriceWithVatC: BigDecimal? = null,

    /**
     * Unit price with VAT (group D)
     */
    @SerialName("unitPriceWithVatD")
    val unitPriceWithVatD: BigDecimal? = null,


    /**
     * Unit price with VAT (group E)
     */
    @SerialName("unitPriceWithVatE")
    val unitPriceWithVatE: BigDecimal? = null,

    /**
     * VAT rate, `null` for exempted VAT.
     */
    @SerialName("vatRate")
    val vatRate: BigDecimal?,

    /**
     * Amount of points to be added to customers account in the moment of product sale.
     */
    @SerialName("points")
    val points: BigDecimal,

    /**
     * The number of points for which the product can be sold or `null`.
     */
    @SerialName("priceInPoints")
    val priceInPoints: BigDecimal?,

    /**
     * Set of [product features][ProductFeature].
     */
    @SerialName("features")
    val features: Set<ProductFeature>,

    /**
     * If set to false, no bulk discounts will be applied to the product.
     */
    @SerialName("allowDiscounts")
    val allowDiscounts: Boolean,

    /**
     * Configures whether the product will be deducted from stock.
     */
    @SerialName("stockDeducted")
    val stockDeducted: Boolean,

    @SerialName("stockOverdraftBehavior")
    val stockOverdraftBehavior: ProductStockOverdraftBehavior,

    @SerialName(WithTags.SERIAL_NAME)
    override val tags: List<String>,

    @SerialName(Displayable.SERIAL_NAME)
    override val display: Boolean,

    @SerialName(Deletable.SERIAL_NAME)
    override val isDeleted: Boolean,

    @SerialName(WithVersion.SERIAL_NAME)
    override val version: Long = WithVersion.DEFAULT_VALUE,
) : BaseEntityDto(),
    WithCustomPrintName,
    WithTags,
    WithColor,
    WithMeasurementUnit,
    Displayable,
    Deletable {

    init {
        validate(this) {
            validate(ProductMigrationDto::id).isValidId()
            validate(ProductMigrationDto::name).isNotEmpty()
            // TODO: printName null || not empty validation
            validate(ProductMigrationDto::version).isPositiveOrZero()
        }
    }

    /**
     * Comparable measurement is used for [price tags printing](http://manual.dotykacka.cz/index.html?tisk-cenovek.html).
     * It show comparable price for product with different quantity.
     *
     * | Product     | Product Qty | Comparable measure. |
     * | ----------- | -----------:| -------------------:|
     * | Butter A    | 1 PIECE     | 150 GRAM            |
     * | Butter B    | 1 PIECE     | 250 GRAM            |
     */
    @Serializable
    data class ComparableMeasurement(
        @SerialName("packaging")
        val packaging: BigDecimal,

        @SerialName(WithMeasurementUnit.SERIAL_NAME)
        override val measurementUnit: MigrationMeasurementUnit
    ) : WithMeasurementUnit
}


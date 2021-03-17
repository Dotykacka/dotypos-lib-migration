@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto

import com.dotypos.lib.migration.dto.config.PosConfigurationDto
import com.dotypos.lib.migration.dto.entity.*
import com.dotypos.lib.migration.dto.entity.iface.WithCountry
import com.dotypos.lib.migration.dto.entity.iface.WithName
import com.dotypos.lib.migration.dto.validation.hasUniqueItemIds
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.valiktor.functions.*
import org.valiktor.validate
import java.util.*

@Serializable
data class PosMigrationDto(
    val metadata: Metadata,

    /**
     * Configuration of migrated POS
     */
    @SerialName("posConfiguration")
    val posConfiguration: PosConfigurationDto,

    /**
     * Employees
     */
    @SerialName("employees")
    val employees: Set<EmployeeMigrationDto>,

    /**
     * Sellers
     */
    @SerialName("sellers")
    val sellers: Set<SellerMigrationDto>,

    /**
     * Sorted list of meal courses
     */
    @SerialName("courses")
    val courses: List<CourseMigrationDto>,

    /**
     * Sorted list of sale categories
     */
    @SerialName("categories")
    val categories: List<CategoryMigrationDto>,

    /**
     * Sorted list of products - order is also preserved in categories
     */
    @SerialName("products")
    val products: List<ProductMigrationDto>,

    /**
     * List of product ingredients used for stock deduction
     */
    @SerialName("ingredients")
    val ingredients: List<ProductIngredientMigrationDto>,

    /**
     * List of customer discount groups definitions
     */
    @SerialName("customerDiscountGroups")
    val customerDiscountGroups: List<CustomerDiscountGroupMigrationDto>,

    /**
     * List of customers
     */
    @SerialName("customers")
    val customers: List<CustomerMigrationDto>,

    /**
     * List of table pages
     */
    @SerialName("tablePages")
    val tablePages: List<TablePageMigrationDto>,

    /**
     * List of tables
     */
    @SerialName("tables")
    val tables: List<TableMigrationDto>,

    /**
     * List of warehouses accessible on POS
     */
    @SerialName("warehouses")
    val warehouses: List<WarehouseMigrationDto>,

    /**
     * List of suppliers
     */
    @SerialName("supplliers")
    val suppliers: List<SupplierMigrationDto>,

    /**
     * List of printers and print tasks
     */
    @SerialName("printers")
    val printers: List<PrinterMigrationDto>,

    /**
     * List of all stock operations - only stock taking records expected
     */
    @SerialName("stockOperations")
    val stockOperations: List<StockOperationMigrationDto>,
) {

    init {
        val sellerIds = sellers.map { it.id }.toSet()
        val employeeIds = employees.map { it.id }.toSet()
        val productIds = products.map { it.id }.toSet()
        val productMap = products.map { it.id to it }.toMap()


        validate(this) {
            validate(PosMigrationDto::employees)
                .hasUniqueItemIds()
                .isValid { items ->
                    items.all { it.sellerId == null || sellerIds.contains(it.sellerId) }
                }
            validate(PosMigrationDto::sellers)
                .hasUniqueItemIds()
                .isValid { items ->
                    items.all { employeeIds.contains(it.employeeId) }
                }
            validate(PosMigrationDto::courses).hasUniqueItemIds()
            validate(PosMigrationDto::categories).hasUniqueItemIds()
            validate(PosMigrationDto::products)
                .hasUniqueItemIds()
                .isValid { items ->
                    val categoryIds = categories.map { it.id }.toSet()
                    items.all { categoryIds.contains(it.categoryId) }
                }
            validate(PosMigrationDto::ingredients)
                .hasUniqueItemIds()
                .isValid { items ->
                    items.all { productIds.contains(it.parentProductId) && productIds.contains(it.ingredientProductId) }
                }
                .isValid { items ->
                    // Same measurement unit group check
                    items.all {
                        val product = productMap[it.ingredientProductId] ?: return@isValid false
                        product.measurementUnit.group == it.measurementUnit.group
                    }
                }
            validate(PosMigrationDto::customerDiscountGroups)
                .hasUniqueItemIds()
            validate(PosMigrationDto::customers).hasUniqueItemIds()
            validate(PosMigrationDto::tablePages).hasUniqueItemIds()
            validate(PosMigrationDto::tables).hasUniqueItemIds()
            validate(PosMigrationDto::warehouses).hasUniqueItemIds()
            validate(PosMigrationDto::suppliers).hasUniqueItemIds()
            validate(PosMigrationDto::printers)
                .hasUniqueItemIds()
                .isValid {
                    // Uniqueness of print tasks
                    val allTaskIds = it.flatMap { it.tasks.map { it.id } }
                    allTaskIds.size == allTaskIds.toSet().size
                }
            validate(PosMigrationDto::stockOperations).hasUniqueItemIds()
            // TODO: Validate all related entities for related entity existence
            // TODO: Validate overlap of tables
        }
    }

    @Serializable
    data class Metadata(
        /**
         * Unique identification of migration file starting with migration prefix string
         */
        @SerialName("migrationId")
        val migrationId: String,

        /**
         * Date of migration file creation
         */
        @SerialName("created")
        val created: Date,

        /**
         * Name and surname of user (Administrator) - used to prefill in Dotypos Setup Guide
         */
        @SerialName("name")
        val name: String? = null,

        /**
         * Registration email of user (Administrator) - used to prefill in Dotypos Setup Guide
         */
        @SerialName("email")
        val email: String?,

        /**
         * Phone number of user (Administrator) - used to prefill in Dotypos Setup Guide
         */
        @SerialName("phone")
        val phone: String? = null,

        /**
         * License key to be used for migration
         */
        @SerialName("licenseKey")
        val licenseKey: String?,

        /**
         * Company info - used for Dotypos Setup Guide - cloud creation
         */
        @SerialName("companyInfo")
        val companyInfo: CompanyInfo? = null,

        /**
         * Source POS metadata
         */
        @SerialName("pos")
        val pos: PosMetadata,
    ) {
        init {
            validate(this) {
                validate(Metadata::migrationId).hasSize(min = 10)
                validate(Metadata::created).isLessThan(Date())
            }
        }

    }

    @Serializable
    data class PosMetadata(
        /**
         * Foreign POS id - used for subsequent migrations
         * Markeeta: shopId/terminalId
         */
        @SerialName("id")
        val id: String
    ) {
        init {
            validate(this) {
                validate(PosMetadata::id).isNotBlank()
            }
        }
    }

    @Serializable
    data class CompanyInfo(
        /**
         * Company ID
         */
        @SerialName("companyId")
        val companyId: String,

        /**
         * Company vat ID
         */
        @SerialName("vatId")
        val vatId: String?,

        /**
         * Legal company name
         */
        @SerialName(WithName.SERIAL_NAME)
        override val name: String,

        /**
         * Address lines (street with number, freeform text)
         */
        @SerialName("address")
        val address: List<String>,

        /**
         * City
         */
        @SerialName("city")
        val city: String,

        /**
         * ZIP code
         */
        @SerialName("zip")
        val zip: String,

        @SerialName(WithCountry.SERIALIZED_NAME)
        override val country: String = WithCountry.DEFAULT_COUNTRY,
    ) : WithName, WithCountry
}
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
import com.dotypos.lib.migration.dto.validation.validateRelationsTo
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import com.dotypos.validator.validation.hasSize
import com.dotypos.validator.validation.isLessThan
import com.dotypos.validator.validation.isNotBlank
import com.dotypos.validator.validationOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
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
    @SerialName("suppliers")
    val suppliers: List<SupplierMigrationDto>,

    /**
     * List of printers and print tasks
     */
    @SerialName("printers")
    val printers: List<PrinterMigrationDto>,

    /**
     * List of all money operations (primarily last register closure)
     */
    @SerialName("moneyOperations")
    val moneyOperations: List<MoneyOperationMigrationDto> = emptyList(),

    /**
     * List of stock transactions (in terms of POS migration = single STOCK_TAKING transaction connected to all operations)
     */
    @SerialName("stockTransactions")
    val stockTransactions: List<StockTransactionMigrationDto> = emptyList(),

    /**
     * List of all stock operations - only stock taking records expected
     */
    @SerialName("stockOperations")
    val stockOperations: List<StockOperationMigrationDto>,
) {

    init {
        validationOf(PosMigrationDto::employees)
            .hasUniqueItemIds()
            .validateRelationsTo(
                key = EmployeeMigrationDto::sellerId,
                entities = PosMigrationDto::sellers,
            )

        validationOf(PosMigrationDto::sellers)
            .hasUniqueItemIds()

        validationOf(PosMigrationDto::courses)
            .hasUniqueItemIds()

        validationOf(PosMigrationDto::categories)
            .hasUniqueItemIds()
            .validateRelationsTo(
                key = CategoryMigrationDto::defaultCourseId,
                entities = PosMigrationDto::courses,
            )

        validationOf(PosMigrationDto::products)
            .hasUniqueItemIds()
            .validateRelationsTo(
                key = ProductMigrationDto::categoryId,
                entities = PosMigrationDto::categories,
            )

        validationOf(PosMigrationDto::ingredients)
            .hasUniqueItemIds()
            .validateRelationsTo(
                key = ProductIngredientMigrationDto::parentProductId,
                entities = PosMigrationDto::products,
            )
            .validateRelationsTo(
                key = ProductIngredientMigrationDto::ingredientProductId,
                entities = PosMigrationDto::products
            )
        // TODO: measurement unit group validation

        validationOf(PosMigrationDto::customerDiscountGroups)
            .hasUniqueItemIds()

        validationOf(PosMigrationDto::customers)
            .hasUniqueItemIds()
            .validateRelationsTo(
                key = CustomerMigrationDto::discountGroupId,
                entities = PosMigrationDto::customerDiscountGroups,
            )

        validationOf(PosMigrationDto::tablePages)
            .hasUniqueItemIds()

        validationOf(PosMigrationDto::tables)
            .hasUniqueItemIds()
            .validateRelationsTo(
                key = TableMigrationDto::tablePageId,
                entities = PosMigrationDto::tablePages,
            )

        validationOf(PosMigrationDto::warehouses)
            .hasUniqueItemIds()

        validationOf(PosMigrationDto::suppliers)
            .hasUniqueItemIds()

        validationOf(PosMigrationDto::printers)
            .hasUniqueItemIds()
        // TODO: PrintTask uniqueness

        validationOf(PosMigrationDto::stockTransactions)
            .hasUniqueItemIds()

        validationOf(PosMigrationDto::stockOperations)
            .hasUniqueItemIds()
            .validateRelationsTo(
                key = StockOperationMigrationDto::stockTransactionId,
                entities = PosMigrationDto::stockTransactions,
            )
            .validateRelationsTo(
                key = StockOperationMigrationDto::productId,
                entities = PosMigrationDto::products,
            )
            .validateRelationsTo(
                key = StockOperationMigrationDto::employeeId,
                entities = PosMigrationDto::employees,
            )
            .validateRelationsTo(
                key = StockOperationMigrationDto::sellerId,
                entities = PosMigrationDto::sellers,
            )
            .validateRelationsTo(
                key = StockOperationMigrationDto::warehouseId,
                entities = PosMigrationDto::warehouses,
            )
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
            validationOf(Metadata::migrationId).hasSize(min = 10)
            validationOf(Metadata::created).isLessThan(Date())
        }
    }

    @Serializable
    data class PosMetadata(
        /**
         * Foreign POS id - used for subsequent migrations
         * Markeeta: shopId/terminalId
         */
        @SerialName("id")
        val id: String,
    ) {
        init {
            validationOf(PosMetadata::id).isNotBlank()
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
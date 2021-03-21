import com.dotypos.lib.migration.dto.entity.CategoryMigrationDto
import com.dotypos.lib.migration.dto.entity.ProductMigrationDto
import com.dotypos.lib.migration.dto.enumerate.MigrationMeasurementUnit
import com.dotypos.lib.migration.dto.enumerate.ProductStockOverdraftBehavior
import com.dotypos.lib.migration.dto.validation.MissingRelatedEntityValidationError
import com.dotypos.validator.UniqueConstraintValidationError
import helper.PosMigrationHelper
import org.junit.jupiter.api.*
import java.math.BigDecimal
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PosMigrationValidationTest {

    val validCategory: CategoryMigrationDto
        get() = CategoryMigrationDto(
            id = 30,
            externalEdiId = null,
            name = "Category name",
            hexColor = "#FFFFFF",
            defaultVatRate = null,
            maxDiscount = null,
            defaultCourseId = null,
            tags = emptyList(),
            fiscalizationDisabled = false,
            isDeleted = false,
            version = 0L
        )

    val validProduct = ProductMigrationDto(
        id = 900719925474099L,
        categoryId = validCategory.id,
        name = "Some name",
        printName = null,
        subtitle = "",
        note = "",
        quickNotes = emptyList(),
        description = "",
        hexColor = "#FF0000",
        packaging = BigDecimal.ONE,
        measurementUnit = MigrationMeasurementUnit.PIECE,
        comparableMeasurement = null,
        ean = emptyList(),
        plu = emptyList(),
        unitPriceWithVat = BigDecimal("12.36"),
        vatRate = BigDecimal("21"),
        points = BigDecimal.ZERO,
        priceInPoints = null,
        features = emptySet(),
        allowDiscounts = true,
        stockDeducted = true,
        stockOverdraftBehavior = ProductStockOverdraftBehavior.ALLOW,
        tags = listOf("kitchen"),
        display = true,
        isDeleted = false,
        version = 0L
    )

    @Nested
    inner class Validation {

        @Test
        fun `Unique product id`() {
            assertThrows<UniqueConstraintValidationError> {
                PosMigrationHelper.createEmptyPosMigration()
                    .copy(
                        products = listOf(validProduct, validProduct)
                    )
            }
        }

        @Test
        fun `Missing relation`() {
            val e = assertThrows<MissingRelatedEntityValidationError> {
                PosMigrationHelper.createEmptyPosMigration()
                    .copy(
                        products = listOf(
                            validProduct.copy(
                                categoryId = 0
                            )
                        ),
                        categories = listOf(validCategory)
                    )
            }
            assertEquals(0L, e.keys.firstOrNull())
        }

        @Test
        fun `Valid relation`() {
            assertDoesNotThrow {
                PosMigrationHelper.createEmptyPosMigration()
                    .copy(
                        products = listOf(validProduct),
                        categories = listOf(validCategory)
                    )
            }
        }
    }
}
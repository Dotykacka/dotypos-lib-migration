import com.dotypos.lib.migration.dto.entity.DocumentItemMigrationDto
import com.dotypos.lib.migration.dto.entity.DocumentMigrationDto
import com.dotypos.lib.migration.dto.enumerate.MigrationMeasurementUnit
import com.dotypos.lib.migration.dto.validation.UnexpectedSumValue
import helper.CloudMigrationHelper
import org.junit.jupiter.api.*
import java.math.BigDecimal
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CloudMigrationValidationTest {

    val validReceipt: DocumentMigrationDto
        get() = DocumentMigrationDto(
            id = 1,
            type = DocumentMigrationDto.Type.RECEIPT,
            relatedDocumentId = null,
            tableId = null,
            created = Date(System.currentTimeMillis() - 30_000L),
            documentNumber = "ABCD12345",
            issueDate = Date(),
            customerId = null,
            employeeId = 3,
            location = DocumentMigrationDto.Location(
                date = Date(),
                latitude = 31.3,
                longitude = -34.3,
                accuracy = 1.2f,
            ),
            note = "Some short note",
            items = listOf(
                DocumentItemMigrationDto(
                    id = 1,
                    productId = 1,
                    categoryId = 1,
                    relatedDocumentItemId = null,
                    courseId = null,
                    type = DocumentItemMigrationDto.Type.COMMON,
                    name = "Sample item",
                    printName = null,
                    subtitle = "",
                    note = "Item note",
                    hexColor = "#FFAABB",
                    packaging = BigDecimal.ONE,
                    measurementUnit = MigrationMeasurementUnit.PIECE,
                    ean = emptyList(),
                    quantity = BigDecimal.ONE,
                    unitPurchasePrice = null,
                    baseUnitPriceWithoutVat = BigDecimal("26"),
                    baseUnitPriceWithVat = BigDecimal("29.9"),
                    billedUnitPriceWithoutVat = BigDecimal("26"),
                    billedUnitPriceWithVat = BigDecimal("29.9"),
                    totalPriceWithoutVat = BigDecimal("26"),
                    totalPriceWithVat = BigDecimal("29.9"),
                    vatRate = BigDecimal("15"),
                    discountPercent = BigDecimal.ZERO,
                    points = BigDecimal.ZERO,
                    priceInPoints = null,
                    stockDeducted = true,
                    tags = emptyList(),
                    version = System.currentTimeMillis(),
                ),
                DocumentItemMigrationDto.createRounding(
                    id = 2,
                    name = "Rounding",
                    printName = null,
                    relatedDocumentItemId = null,
                    roundingValue = BigDecimal("0.10")
                )
            ),
            totalValue = BigDecimal("30"),
            currency = "CZK",
            foreignCurrency = null,
            isPaid = true,
            points = BigDecimal.ZERO,
            issuedByVatPayer = true,
            czFiscalizationData = null,
            onBehalfSaleSubjectId = null,
            externalId = null,
            sellerId = null,
            tags = emptyList(),
            isDelivery = false,
            isReverseCharge = false,
            printWithLunchInvitation = false,
            welmecMode = false,
            printData = "",
            merchantPrintData = "",
            version = System.currentTimeMillis(),
        )

    @Nested
    inner class Validation {

        @Test
        fun `Valid order item sum`() {
            assertDoesNotThrow {
                CloudMigrationHelper.createEmptyCloudMigration()
                    .copy(
                        documents = listOf(
                            validReceipt
                        )
                    )
            }
        }

        @Test
        fun `Invalid order item sum`() {
            val e = assertThrows<UnexpectedSumValue> {
                val receipt = validReceipt
                CloudMigrationHelper.createEmptyCloudMigration()
                    .copy(
                        documents = listOf(
                            receipt.copy(
                                items = receipt.items + DocumentItemMigrationDto.createRounding(
                                    id = 10,
                                    name = "Broken rounding",
                                    printName = null,
                                    relatedDocumentItemId = null,
                                    roundingValue = BigDecimal.ONE,
                                )
                            )
                        )
                    )
            }
        }
    }
}
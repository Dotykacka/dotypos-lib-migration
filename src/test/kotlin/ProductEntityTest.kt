import com.dotypos.lib.migration.dto.entity.ProductMigrationDto
import com.dotypos.lib.migration.dto.enumerate.MigrationMeasurementUnit
import com.dotypos.lib.migration.dto.enumerate.ProductStockOverdraftBehavior
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationException
import java.math.BigDecimal
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductEntityTest {

    val validProduct = ProductMigrationDto(
        id = 900719925474099L,
        categoryId = 0,
        name = "My testing product",
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
        fun `id out of range validation`() {
            assertThrows<ConstraintViolationException> {
                validProduct.copy(id = 90071992547409920L)
            }
        }
    }

    @Nested
    inner class Serialization {

        @Test
        fun `valid item serialization`() {
            val expected = """
{"id":900719925474099,"categoryId":0,"name":"My testing product","subtitle":"","note":"","quickNotes":[],"description":"","hexColor":"#FF0000","packaging":"1","measurementUnit":"piece","comparableMeasurement":null,"ean":[],"plu":[],"unitPriceWithVat":"12.36","vatRate":"21","points":"0","priceInPoints":null,"features":[],"allowDiscounts":true,"stockDeducted":true,"stockOverdraftBehavior":"allow","tags":["kitchen"],"display":true,"deleted":false}
           """.trimIndent()
            val encoded = Json.encodeToString(validProduct)
            assertEquals(expected, encoded)
        }
    }
}
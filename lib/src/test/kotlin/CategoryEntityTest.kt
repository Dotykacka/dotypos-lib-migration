import com.dotypos.lib.migration.dto.entity.CategoryMigrationDto
import com.dotypos.validator.SimpleConstraintValidationError
import org.junit.jupiter.api.*
import java.math.BigDecimal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryEntityTest {

    val validCategory: CategoryMigrationDto
        get() = CategoryMigrationDto(
            id = 900719925474099L,
            externalEdiId = null,
            name = "Category name",
            hexColor = "#FFFFFF",
            defaultVatRate = BigDecimal("21"),
            maxDiscount = null,
            defaultCourseId = null,
            tags = emptyList(),
            fiscalizationDisabled = false,
            isDeleted = false,
            version = 2000,
        )

    @Nested
    inner class Validation {

        @Test
        fun `id out of range`() {
            assertThrows<SimpleConstraintValidationError> {
                validCategory.copy(id = Long.MAX_VALUE)
            }
        }

        @Test
        fun `Empty name`() {
            assertThrows<SimpleConstraintValidationError> {
                validCategory.copy(name = "")
            }
        }

        @Test
        fun `Positive version`() {
            assertThrows<SimpleConstraintValidationError> {
                validCategory.copy(version = -10)
            }
        }

        @Test
        fun `Valid entity`() {
            assertDoesNotThrow {
                validCategory
            }
        }
    }

    @Nested
    inner class Serialization {

        // TODO: serialization test
    }
}
import com.dotypos.validator.ErrorCollector
import com.dotypos.validator.ValidationError
import com.dotypos.validator.validation.isGreaterThan
import com.dotypos.validator.validation.isNotBlank
import com.dotypos.validator.validationOf
import com.dotypos.validator.withSafeValidation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ErrorCollectorTest {

    @Test
    fun `Single validation error`() {
        val result = withSafeValidation {
            ComplexEntity(
                id = 1,
                text = "ABCD",
                otherText = null,
            )
        }
        assertTrue { result is ErrorCollector.ValidationResult.Error }
        val errorsCount = (result as ErrorCollector.ValidationResult.Error).errors.size
        assertEquals(1, errorsCount)
    }

    @Test
    fun `Multiple validation errors`() {
        val result = withSafeValidation {
            ComplexEntity(
                id = 1,
                text = "",
                otherText = null,
            )
        }
        assertTrue { result is ErrorCollector.ValidationResult.Error }
        val errorsCount = (result as ErrorCollector.ValidationResult.Error).errors.size
        assertEquals(2, errorsCount)
    }

    @Test
    fun `Successful validation`() {
        val result = withSafeValidation {
            ComplexEntity(
                id = 4,
                text = "Some text",
                otherText = null,
            )
        }
        assertTrue { result is ErrorCollector.ValidationResult.Success }
    }

    @Test
    fun `Multiple validation errors without collector`() {
        assertThrows<ValidationError> {
            ComplexEntity(
                id = 1,
                text = "",
                otherText = null,
            )
        }
    }

    private data class ComplexEntity(
        val id: Long,
        val text: String,
        val otherText: String?,
    ) {
        init {
            validationOf(ComplexEntity::id)
                .isGreaterThan(3L)

            validationOf(ComplexEntity::text)
                .isNotBlank()
        }
    }
}
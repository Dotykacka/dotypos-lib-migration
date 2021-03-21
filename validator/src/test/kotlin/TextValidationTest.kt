import com.dotypos.validator.SimpleConstraintValidationError
import com.dotypos.validator.ValidationError
import com.dotypos.validator.validation.*
import com.dotypos.validator.validationOf
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TextValidationTest {
    @Nested
    inner class CharSequence {

        @Test
        fun `Text is not empty validation failed`() {
            assertThrows<SimpleConstraintValidationError> {
                WrapperObject(text = "")
                    .validationOf(WrapperObject::text)
                    .isNotEmpty()
            }
        }

        @Test
        fun `Text is empty validation failed`() {
            assertThrows<SimpleConstraintValidationError> {
                WrapperObject(text = "ASDF")
                    .validationOf(WrapperObject::text)
                    .isEmpty()
            }
        }

        @Test
        fun `Text has required length`() {
            assertDoesNotThrow {
                WrapperObject(text = "ABCD").validationOf(WrapperObject::text)
                    .hasSize(min = 1)
                    .hasSize(max = 5)
                    .hasSize(min = 1, max = 5)

                WrapperObject(text = "X").validationOf(WrapperObject::text)
                    .hasSize(min = 1)
                    .hasSize(max = 1)
                    .hasSize(min = 1, max = 1)
            }
        }

        @Test
        fun `Text is short`() {
            assertThrows<ValidationError> {
                WrapperObject(text = "ABCD").validationOf(WrapperObject::text).hasSize(min = 5)
            }
            assertThrows<ValidationError> {
                WrapperObject(text = "ABCD").validationOf(WrapperObject::text).hasSize(min = 5, max = 10)
            }
        }

        @Test
        fun `Text is long`() {
            assertThrows<ValidationError> {
                WrapperObject(text = "ABCD").validationOf(WrapperObject::text).hasSize(max = 3)
            }
            assertThrows<ValidationError> {
                WrapperObject(text = "ABCD").validationOf(WrapperObject::text).hasSize(min = 1, max = 3)
            }
        }
    }

    @Nested
    inner class FormattedText {

        @Test
        fun `Validate color`() {
            // Valid
            assertDoesNotThrow {
                WrapperObject(text = "FFAABB22").validationOf(WrapperObject::text).isValidColor()
                WrapperObject(text = "AABB22").validationOf(WrapperObject::text).isValidColor()
            }
        }

        @Test
        fun `Validate wrong color`() {
            // Invalid
            assertThrows<ValidationError> {
                WrapperObject(text = "HHAABB66").validationOf(WrapperObject::text).isValidColor()
            }
        }

        @Test
        fun `Validate currency`() {
            // Valid
            assertDoesNotThrow {
                WrapperObject(text = "CZK").validationOf(WrapperObject::text).isValidCurrency()
                WrapperObject(text = "EUR").validationOf(WrapperObject::text).isValidCurrency()
                WrapperObject(text = "SKK").validationOf(WrapperObject::text).isValidCurrency()
            }
        }

        @Test
        fun `Validate wrong currency`() {
            // Invalid
            assertThrows<ValidationError> {
                WrapperObject(text = "123").validationOf(WrapperObject::text).isValidCurrency()
            }
        }

        @Test
        fun `Validate country`() {
            // Valid
            assertDoesNotThrow {
                WrapperObject(text = "CZ").validationOf(WrapperObject::text).isValidCountry()
            }
        }

        @Test
        fun `Validate wrong country`() {
            // Invalid
            assertThrows<ValidationError> {
                WrapperObject(text = "CZE").validationOf(WrapperObject::text).isValidCountry()
            }
        }
    }

}

private data class WrapperObject(
    val text: String = "",
)
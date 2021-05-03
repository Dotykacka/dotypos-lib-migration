@file:UseSerializers(RetentionPeriodSerializer::class)

package serialization

import com.dotypos.lib.migration.dto.config.RetentionPeriod
import com.dotypos.lib.migration.serialization.RetentionPeriodSerializer
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RetentionPeriodSerializationDeserializationTest {

    @Nested
    inner class Serialization {

        @Test
        fun `Valid serialization of 2 weeks retention`() {
            val period = RetentionPeriod(value = 2, unit = RetentionPeriod.RetentionUnit.WEEK)
            val string = period.toString()
            assertEquals("2w", string)
        }

        @Test
        fun `Valid serialization of 3 month retention`() {
            val period = RetentionPeriod(value = 3, unit = RetentionPeriod.RetentionUnit.MONTH)
            val string = period.toString()
            assertEquals("3m", string)
        }
    }

    @Nested
    inner class Deserialization {

        @Test
        fun `Valid deserialization of 3 weeks retention`() {
            val string = "3w"
            val period = RetentionPeriod.parse(string)
            assertEquals(3L, period.value)
            assertEquals(RetentionPeriod.RetentionUnit.WEEK, period.unit)
        }

        @Test
        fun `Fallback deserialization of invalid input`() {
            val string = "asdf"
            val period = RetentionPeriod.parse(string)
            assertEquals(RetentionPeriod.DEFAULT_RETENTION_PERIOD.value, period.value)
            assertEquals(RetentionPeriod.DEFAULT_RETENTION_PERIOD.unit, period.unit)
        }

        @Test
        fun `Fallback deserialization of invalid input 2`() {
            val string = "8ew"
            val period = RetentionPeriod.parse(string)
            assertEquals(RetentionPeriod.DEFAULT_RETENTION_PERIOD.value, period.value)
            assertEquals(RetentionPeriod.DEFAULT_RETENTION_PERIOD.unit, period.unit)
        }
    }

    @Nested
    inner class Serializer {

        private val json = Json

        @Test
        fun `Valid serialization 4 months`() {
            val retention = RetentionPeriod(value = 4, unit = RetentionPeriod.RetentionUnit.WEEK)
            val string = json.encodeToString(RetentionWrapper(retention))
            assertEquals(
                expected = """
                    {"value":"4w"}
                """.trimIndent(),
                actual = string
            )
        }

        @Test
        fun `Valid serialization 1 quarter`() {
            val retention = RetentionPeriod(value = 1, unit = RetentionPeriod.RetentionUnit.QUARTER)
            val string = json.encodeToString(RetentionWrapper(retention))
            assertEquals(
                expected = """
                    {"value":"1q"}
                """.trimIndent(),
                actual = string
            )
        }

        @Test
        fun `Valid deserialization 4 weeks`() {
            val string = """{"value":"4w"}""".trimIndent()
            val wrapper = json.decodeFromString<RetentionWrapper>(string)
            assertEquals(4L, wrapper.value.value)
            assertEquals(RetentionPeriod.RetentionUnit.WEEK, wrapper.value.unit)
        }

        @Test
        fun `Fallback deserialization of invalid value`() {
            val string = """{"value":"4ew"}""".trimIndent()
            val wrapper = json.decodeFromString<RetentionWrapper>(string)
            assertEquals(RetentionPeriod.DEFAULT_RETENTION_PERIOD.value, wrapper.value.value)
            assertEquals(RetentionPeriod.DEFAULT_RETENTION_PERIOD.unit, wrapper.value.unit)
            assertEquals(RetentionPeriod.DEFAULT_RETENTION_PERIOD, wrapper.value)
        }

        @Test
        fun `Fallback deserialization of missing value`() {
            val string = """{}""".trimIndent()
            val wrapper = json.decodeFromString<RetentionWrapperWithDefaultValue>(string)
            assertEquals(RetentionPeriod.DEFAULT_RETENTION_PERIOD.value, wrapper.value.value)
            assertEquals(RetentionPeriod.DEFAULT_RETENTION_PERIOD.unit, wrapper.value.unit)
            assertEquals(RetentionPeriod.DEFAULT_RETENTION_PERIOD, wrapper.value)
        }
    }

    @Serializable
    private data class RetentionWrapper(
        @SerialName(value = "value")
        val value: RetentionPeriod,
    )

    @Serializable
    private data class RetentionWrapperWithDefaultValue(
        @SerialName(value = "value")
        val value: RetentionPeriod = RetentionPeriod.DEFAULT_RETENTION_PERIOD,
    )
}
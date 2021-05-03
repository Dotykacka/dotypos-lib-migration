package com.dotypos.lib.migration.serialization

import com.dotypos.lib.migration.dto.config.RetentionPeriod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RetentionPeriodSerializer : KSerializer<RetentionPeriod> {

    override val descriptor = PrimitiveSerialDescriptor("RetentionPeriod", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): RetentionPeriod {
        return decoder.decodeString().let(RetentionPeriod.Companion::parse)
            ?: RetentionPeriod.DEFAULT_RETENTION_PERIOD
    }

    override fun serialize(encoder: Encoder, value: RetentionPeriod) {
        encoder.encodeString(value.toString())
    }
}
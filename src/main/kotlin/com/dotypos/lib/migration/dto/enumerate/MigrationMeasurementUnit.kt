package com.dotypos.lib.migration.dto.enumerate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MigrationMeasurementUnit(val group: Group) {
    // GROUP COUNT
    @SerialName("piece")
    PIECE(Group.COUNT),

    // GROUP WEIGHT
    @SerialName("milligram")
    MILLIGRAM(Group.WEIGHT),

    @SerialName("decagram")
    DECAGRAM(Group.WEIGHT),

    @SerialName("gram")
    GRAM(Group.WEIGHT),

    @SerialName("kilogram")
    KILOGRAM(Group.WEIGHT),

    @SerialName("quintal")
    QUINTAL(Group.WEIGHT),

    @SerialName("tonne")
    TONNE(Group.WEIGHT),

    @SerialName("pound")
    POUND(Group.WEIGHT),

    @SerialName("ounce")
    OUNCE(Group.WEIGHT),

    // LENGTH
    @SerialName("millimeter")
    MILLIMETER(Group.LENGTH),

    @SerialName("centimeter")
    CENTIMETER(Group.LENGTH),

    @SerialName("meter")
    METER(Group.LENGTH),

    @SerialName("kilometer")
    KILOMETER(Group.LENGTH),

    // Imperial
    @SerialName("inch")
    INCH(Group.LENGTH),

    @SerialName("mile")
    MILE(Group.LENGTH),

    // AREA
    @SerialName("sqMeter")
    SQ_METER(Group.AREA),

    @SerialName("sqFoot")
    SQ_FOOT(Group.AREA),

    // VOLUME
    @SerialName("milliliter")
    MILLILITER(Group.VOLUME),

    @SerialName("centiliter")
    CENTILITER(Group.VOLUME),

    @SerialName("deciliter")
    DECILITER(Group.VOLUME),

    @SerialName("liter")
    LITER(Group.VOLUME),

    @SerialName("usGallon")
    US_GALLON(Group.VOLUME),

    @SerialName("ukGallon")
    UK_GALLON(Group.VOLUME),

    @SerialName("cubicFoot")
    CUBIC_FOOT(Group.VOLUME),

    @SerialName("hectoliter")
    HECTOLITER(Group.VOLUME),

    @SerialName("cubicMeter")
    CUBIC_METER(Group.VOLUME),

    // TIME
    @SerialName("second")
    SECOND(Group.TIME),

    @SerialName("minute")
    MINUTE(Group.TIME),

    @SerialName("hour")
    HOUR(Group.TIME),

    @SerialName("day")
    DAY(Group.TIME),

    @SerialName("week")
    WEEK(Group.TIME),

    @SerialName("month")
    MONTH(Group.TIME),

    @SerialName("year")
    YEAR(Group.TIME),

    // POINTS
    @SerialName("point")
    POINT(Group.POINTS),
    ;

    enum class Group {
        COUNT,
        WEIGHT,
        LENGTH,
        AREA,
        VOLUME,
        TIME,
        POINTS
    }

}
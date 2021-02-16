package com.dotypos.lib.migration.demo.creator

import com.dotypos.lib.migration.dto.PosMigrationDto
import com.dotypos.lib.migration.dto.entity.CategoryMigrationDto
import com.dotypos.lib.migration.dto.entity.ProductMigrationDto
import com.dotypos.lib.migration.dto.enumerate.MigrationMeasurementUnit
import com.dotypos.lib.migration.dto.enumerate.ProductStockOverdraftBehavior
import java.math.BigDecimal
import kotlin.math.pow
import kotlin.random.Random

class LargeDemoDataCreatorV1(private val seed: Long) : PosDataCreator {

    var categoriesCount = 200
    var productsCount = 10_000

    val categories by lazy {
        val random = Random(seed)
        emptyList<CategoryMigrationDto>()
        List(categoriesCount) { id -> createCategory(id.toLong(), random) }
    }

    val products by lazy {
        val random = Random(seed)
        List(productsCount) { id -> createProduct(id.toLong(), random) }
    }

    override fun createPosData(): PosMigrationDto {
        val baseData = EmptyDemoDataCreator.createPosData()

        return baseData.copy(
            categories = categories,
            products = products,
        )
    }

    private fun createCategory(id: Long, random: Random) = CategoryMigrationDto(
        id = id,
        externalEdiId = null,
        name = colorNames.random(random),
        hexColor = getColor(random),
        defaultVatRate = vatRates.random(random),
        maxDiscount = null,
        defaultCourseId = null,
        tags = emptyList(),
        fiscalizationDisabled = false,
        isDeleted = false,
    )


    private fun createProduct(id: Long, random: Random) = ProductMigrationDto(
        id = id,
        categoryId = categories.random(random).id,
        name = fruits.random(random),
        subtitle = herbs.random(random),
        note = List(random.nextInt(0, 20)) { fruits.random(random) }
            .joinToString(separator = " "),
        quickNotes = emptyList(),
        description = "",
        hexColor = getColor(random),
        packaging = BigDecimal.ONE,
        measurementUnit = MigrationMeasurementUnit.PIECE,
        comparableMeasurement = null,
        ean = emptyList(),
        plu = emptyList(),
        unitPriceWithVat = random.nextBigDecimal(max = 30_000),
        vatRate = vatRates.random(random),
        points = BigDecimal.ZERO,
        priceInPoints = BigDecimal.ZERO,
        features = emptySet(),
        allowDiscounts = random.nextBoolean(),
        stockDeducted = random.nextBoolean(),
        stockOverdraftBehavior = ProductStockOverdraftBehavior.ALLOW,
        tags = emptyList(),
        display = true,
        isDeleted = false,
    )

    private fun Random.nextBigDecimal(min: Int = 0, max: Int = Int.MAX_VALUE, decimals: Int = 2): BigDecimal {
        return BigDecimal(nextLong(min.toLong(), max.toLong()) / 10.0.pow(decimals))
    }
}
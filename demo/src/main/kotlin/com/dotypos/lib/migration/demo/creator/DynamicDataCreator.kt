package com.dotypos.lib.migration.demo.creator

import com.dotypos.lib.migration.dto.CloudMigrationDto
import com.dotypos.lib.migration.dto.PosMigrationDto
import com.dotypos.lib.migration.dto.config.CzFiscalizationConfiguration
import com.dotypos.lib.migration.dto.entity.*
import com.dotypos.lib.migration.dto.enumerate.MigrationMeasurementUnit
import com.dotypos.lib.migration.dto.enumerate.PrintTaskType
import com.dotypos.lib.migration.dto.enumerate.PrinterConnectionMode
import com.dotypos.lib.migration.dto.enumerate.ProductStockOverdraftBehavior
import com.dotypos.lib.migration.dto.enumerate.permission.EmployeeMobileWaiterPermission
import com.dotypos.lib.migration.dto.enumerate.permission.EmployeePosPermission
import com.dotypos.lib.migration.dto.enumerate.permission.EmployeeStockPermission
import com.dotypos.lib.migration.util.KeystoreUtil
import com.dotypos.lib.migration.util.KeystoreUtil.repackage
import com.dotypos.lib.migration.util.KeystoreUtil.toBase64
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.create
import java.lang.IllegalStateException
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.math.pow
import kotlin.random.Random

class DynamicDataCreator(
    private val seed: Long,
    private val employees: Int = 20,
    private val products: Int = 30_000,
    private val ingredients: Int = 20_000,
    private val categories: Int = 200,
    private val customers: Int = 20_000,
    private val discountGroups: Int = 5,
    private val courses: Int = 0,
    private val tablePages: Int = 0,
    private val tables: Int = 0,
    private val warehouses: Int = 1,
    private val suppliers: Int = 20,
    private val printers: Int = 1,
    private val documents: Int = 20_000,
    private val withEet: Boolean = true,
) : PosDataCreator, CloudDataCreator {

    private val fakerConfig = FakerConfig.builder().create {
        random = java.util.Random(seed)
    }
    private val faker = Faker(fakerConfig)

    // POS DATA
    private val migrationId = Date().toString()
    private val cloudId = "123"
    private val branchId = "321"
    private val employeesList by randomList(employees, ::createEmployee)
    private val categoryList by randomList(categories, ::createCategory)
    private val productList by randomList(products, ::createProduct)
    private val ingredientList by lazy { createIngredients() }
    private val discountGroupList by randomList(discountGroups, ::createDiscountGroup)
    private val customerList by randomList(customers, ::createCustomer)
    private val courseList by randomList(courses, ::createCourse)
    private val tablePageList by randomList(tablePages, ::createTablePage)
    private val tableList by randomList(tables, ::createTable)
    private val warehouseList by randomList(warehouses, ::createWarehouse)
    private val supplierList by randomList(suppliers, ::createSupplier)
    private val printerList by randomList(printers, ::createPrinter)

    private val czFiscalizationConfigurations by randomList(1, ::createCzFiscalizationConfiguration)

    override fun createPosData(): PosMigrationDto {
        val baseData = EmptyDemoDataCreator.createPosData()

        return baseData.copy(
            metadata = PosMigrationDto.Metadata(
                migrationId = migrationId,
                created = Date(System.currentTimeMillis() - 1L),
                email = "john.doe@example.com",
                licenseKey = "EXAMPLE",
                pos = PosMigrationDto.PosMetadata(
                    id = "EMPTY",
                ),
            ),
            posConfiguration = baseData.posConfiguration.copy(
                czFiscalizationConfigurations = czFiscalizationConfigurations,
            ),
            employees = employeesList.toSet(),
            sellers = emptySet(),
            courses = courseList,
            categories = categoryList,
            products = productList,
            ingredients = ingredientList,
            customerDiscountGroups = discountGroupList,
            customers = customerList,
            tablePages = tablePageList,
            tables = tableList,
            warehouses = warehouseList,
            suppliers = supplierList,
            printers = printerList,
        )
    }

    override fun createCloudData(): CloudMigrationDto {
        val random = Random(seed)
        val baseData = EmptyDemoDataCreator.createCloudData()

        val documentList = mutableListOf<DocumentMigrationDto>()
        var created = Calendar.getInstance()
            .apply { set(Calendar.YEAR, 2018) }
            .timeInMillis

        repeat(documents) { pos ->

            val total = BigDecimal.ZERO
            val totalPoints = BigDecimal.ZERO

            val foreignCurrencyCode = random.valueOrNull(20) { CURRENCIES.keys.random(random) }

            val paid = random.nextBoolean()
            documentList += DocumentMigrationDto(
                pos.toLong(),
                type = DocumentMigrationDto.Type.RECEIPT,
                relatedDocumentId = null,
                tableId = random.valueOrNull(2) { tableList.randomOrNull()?.id },
                created = Date(created),
                documentNumber = "ADFS-${pos}",
                issueDate = Date(created + random.nextLong(2 * 60_000, 30 * 60_000)),
                customerId = random.valueOrNull(10) { customerList.random().id },
                employeeId = employeesList.random().id,
                location = random.valueOrNull(2) {
                    DocumentMigrationDto.Location(
                        date = Date(created),
                        accuracy = random.nextFloat() * 5,
                        latitude = random.nextDouble() * 180 - 90,
                        longitude = random.nextDouble() * 360 - 180,
                    )
                },
                note = random.valueOrDefault(4, "") { randomLorem(random.nextInt(3, 25)) },
                items = emptyList(),
                totalValue = total,
                currency = "CZK",
                foreignCurrency = foreignCurrencyCode?.let { code ->
                    DocumentMigrationDto.ForeignCurrency(
                        code = code,
                        exchangeRate = (CURRENCIES.getOrDefault(code, 0.0) * random.nextDouble(0.8, 1.2)).toBigDecimal()
                    )
                },
                isPaid = paid,
                points = totalPoints,
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
                printData = random.valueOrDefault(3, "") {
                    randomLorem(30)
                },
                version = System.currentTimeMillis()
            )
            created += random.nextLong(5 * 60_000, 120 * 60_000)
        }

        return baseData.copy(
            migrationResultData = "$cloudId-$branchId-metadata",
            documents = documentList,
        )
    }

    private fun createEmployee(id: Long, random: Random): EmployeeMigrationDto {
        val name = faker.name.name()
        val pin = random.valueOrNull(2) {
            EmployeeMigrationDto.PinWrapper(
                type = EmployeeMigrationDto.PinWrapper.Type.PLAINTEXT,
                data = random.nextInt(0, 9999).toString(),
            )
        }
        return EmployeeMigrationDto(
            id = id,
            name = name,
            email = random.valueOrNull(4) { faker.internet.email(name) },
            hexColor = getColor(random),
            phone = random.valueOrNull(6, faker.phoneNumber::cellPhone),
            tags = emptyList(),
            maxDiscount = random.valueOrNull(3) { random.nextBigDecimal(5, 30) },
            sellerId = null,
            pin = pin,
            isPinRequired = pin != null,
            posPermissions = EmployeePosPermission.values().filter { random.nextBoolean() }.toSet(),
            stockPermissions = EmployeeStockPermission.values().filter { random.nextBoolean() }.toSet(),
            mobileWaiterPermissions = EmployeeMobileWaiterPermission.values().filter { random.nextBoolean() }.toSet(),
            isEnabled = random.valueOrDefault(5, true) { false },
            isDeleted = random.valueOrDefault(20, true) { false },
            version = System.currentTimeMillis(),
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
        categoryId = categoryList.random(random).id,
        name = fruits.random(random),
        subtitle = herbs.random(random),
        note = List(random.nextInt(0, 20)) { fruits.random(random) }
            .joinToString(separator = " "),
        quickNotes = emptyList(),
        description = "",
        hexColor = getColor(random),
        packaging = BigDecimal.ONE,
        measurementUnit = MigrationMeasurementUnit.values().random(random),
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

    private fun createIngredients(): List<ProductIngredientMigrationDto> {
        val random = Random(seed)
        val ingredientProductsCount = random.nextInt(maxOf(products / ingredients, 1), minOf(ingredients, products))
        val productIds = List(products) { id -> id.toLong() }.toMutableList()
        val ingredientProductsIds = mutableSetOf<Long>()
        repeat(ingredientProductsCount) {
            val ingredientId = productIds.random(random)
            productIds -= ingredientId
            ingredientProductsIds += ingredientId
        }
        return List(ingredients) { id ->
            val ingredientProductId = ingredientProductsIds.random()
            val ingredientProduct = productList[ingredientProductId.toInt()]
            ProductIngredientMigrationDto(
                id = id.toLong(),
                parentProductId = productIds.random(),
                ingredientProductId = ingredientProductId,
                quantity = random.nextBigDecimal(1, 20),
                measurementUnit = MigrationMeasurementUnit.values()
                    .filter { ingredientProduct.measurementUnit.group == it.group }
                    .random(random),
                isDeleted = false,
                version = System.currentTimeMillis(),
            )
        }
    }


    private fun createDiscountGroup(id: Long, random: Random): CustomerDiscountGroupMigrationDto {
        return CustomerDiscountGroupMigrationDto(
            id = id,
            name = faker.futurama.characters(),
            discountPercent = random.nextBigDecimal(0, 50),
            isDeleted = false,
            version = System.currentTimeMillis(),
        )
    }

    private fun createCustomer(id: Long, random: Random): CustomerMigrationDto {
        val firstName = faker.name.firstName()
        val lastName = faker.name.lastName()
        val companyName = random.valueOrDefault(4, "", faker.company::name)
        val withAddress = random.nextInt() % 5 == 0

        return CustomerMigrationDto(
            id = id,
            firstName = firstName,
            lastName = lastName,
            companyName = companyName,
            companyId = if (companyName.isNotEmpty()) random.nextCompanyId() else "",
            companyId2 = null,
            vatId = if (companyName.isNotEmpty()) {
                random.valueOrDefault(2, "") {
                    random.nextVatId(faker.address.countryCode())
                }
            } else {
                ""
            },
            email = random.valueOrNull(3) {
                faker.internet.email("$firstName $lastName")
            },
            phone = random.valueOrNull(5, faker.phoneNumber::cellPhone),
            address = listOfNotNull(
                if (withAddress) random.valueOrNull(20, faker.address::mailbox) else null,
                if (withAddress) faker.address.streetAddress() else null,
            ),
            city = if (withAddress) faker.address.city() else "",
            zip = if (withAddress) faker.address.postcode() else "",
            barcode = random.valueOrNull(30) {
                random.nextLong(100000, 99999999).toString()
            } ?: "",
            expirationDate = random.valueOrNull(100) {
                val now = System.currentTimeMillis()
                val year = 365 * 24 * 60 * 60_000L
                Date(random.nextLong(now - year, now + year))
            },
            note = random.valueOrDefault(20, "", faker.vForVendetta::quotes),
            points = random.valueOrDefault(40, BigDecimal.ZERO) {
                random.nextBigDecimal(10, 1000)
            },
            tags = emptyList(),
            discountGroupId = discountGroupList.random().id,
            sellerId = null, // TODO: Seller migration
            isDeleted = random.valueOrDefault(150, true) { false },
            version = System.currentTimeMillis(),
        )
    }

    private fun createCourse(id: Long, random: Random): CourseMigrationDto {
        return CourseMigrationDto(
            id = id,
            name = faker.food.dish(),
            tags = emptyList(),
            isEnabled = true,
            isDeleted = true,
            version = System.currentTimeMillis(),
        )
    }

    private fun createTablePage(id: Long, random: Random): TablePageMigrationDto {
        return TablePageMigrationDto(
            id = id,
            name = id.toString(),
            isDeleted = false,
            version = System.currentTimeMillis(),
        )
    }

    private fun createTable(id: Long, random: Random): TableMigrationDto {
        val type = TableMigrationDto.TableType.values().random(random)
        return TableMigrationDto(
            id = id,
            sellerId = null,
            tablePageId = id % tablePages,
            name = "t$id",
            type = type,
            seats = type.defaultSeats,
            positionX = random.nextInt(0, 10) * TableMigrationDto.TableType.CIRCLE4.width,
            positionY = random.nextInt(0, 40) * TableMigrationDto.TableType.CIRCLE4.height,
            rotation = if (random.nextBoolean()) 0 else 90,
            tags = emptyList(),
            isDeleted = false,
            version = System.currentTimeMillis(),
        )
    }

    private fun createWarehouse(id: Long, random: Random): WarehouseMigrationDto {
        return WarehouseMigrationDto(
            id = id,
            name = faker.address.city(),
            isEnabled = true,
            isDeleted = false,
            version = System.currentTimeMillis(),
        )
    }

    private fun createSupplier(id: Long, random: Random): SupplierMigrationDto {
        return SupplierMigrationDto(
            id = id,
            name = faker.company.name(),
            companyId = random.nextCompanyId(),
            companyId2 = null,
            vatId = random.nextVatId(faker.address.countryCode()),
            address = listOfNotNull(
                random.valueOrNull(2, faker.address::mailbox),
                faker.address.streetAddress(),
            ),
            city = faker.address.city(),
            zip = faker.address.postcode(),
            country = "",
            email = random.valueOrNull(2, faker.internet::email),
            phone = random.valueOrNull(3, faker.phoneNumber::cellPhone),
            isDeleted = false,
            version = System.currentTimeMillis(),
        )
    }

    private fun createPrinter(id: Long, random: Random): PrinterMigrationDto {
        if (id == 0L) {
            return createDefaultPrinter(id);
        }
        val connectionMode = PrinterConnectionMode.values().random(random)
        val address = when (connectionMode) {
            PrinterConnectionMode.USB -> "1234:5678"
            PrinterConnectionMode.NETWORK -> {
                List(4) { random.nextInt(0, 254) }
                    .joinToString(".")
            }
            PrinterConnectionMode.BLUETOOTH -> {
                List(6) { random.nextInt(0, 255).toString(16) }
                    .joinToString(separator = ":")
            }
            PrinterConnectionMode.INTERNAL_SUNMI_V1,
            PrinterConnectionMode.INTERNAL_SUNMI_V2,
            PrinterConnectionMode.INTERNAL_LANDI_A8 -> "local"
        }
        val largePrinter = when (connectionMode) {
            PrinterConnectionMode.INTERNAL_SUNMI_V1,
            PrinterConnectionMode.INTERNAL_SUNMI_V2,
            PrinterConnectionMode.INTERNAL_LANDI_A8 -> false
            else -> random.nextBoolean()
        }

        return PrinterMigrationDto(
            id = id,
            name = "${faker.device.manufacturer()} ${faker.device.modelName()}",
            connectionMode = connectionMode,
            address = address,
            encoding = PrinterMigrationDto.DEFAULT_ENCODING,
            charactersFontA = if (largePrinter) 42 else 32,
            charactersFontB = if (largePrinter) 42 else 32,
            appendLines = 2,
            canBeep = largePrinter,
            canCut = largePrinter && random.nextBoolean(),
            withDrawer = largePrinter,
            tasks = List(random.nextInt(0, 5)) { pos ->
                createPrintTask(id * 1000L + pos.toLong(), random)
            },
            isDeleted = false,
            version = System.currentTimeMillis(),
        )
    }

    private fun createPrintTask(id: Long, random: Random): PrintTaskMigrationDto {
        val type = PrintTaskType.values().random()
        return PrintTaskMigrationDto(
            id = id,
            type = type,
            header = ifOrNull(type in setOf(PrintTaskType.RECEIPT, PrintTaskType.INVOICE)) {
                random.valueOrNull(
                    occurence = 2,
                    getValue = { List(random.nextInt(3, 20)) { faker.lorem.words() }.joinToString(" ") }
                )
            },
            logo = ifOrNull(type == PrintTaskType.RECEIPT) {
                random.valueOrNull(
                    occurence = 2,
                    getValue = { logos.random(random).toBase64() }
                )
            },
            copies = 1,
            beep = random.nextBoolean(),
            useFontB = random.nextBoolean(),
            filters = emptyList(), // TODO: add tags migration
            version = System.currentTimeMillis(),
        )
    }

    private fun createDefaultPrinter(id: Long) = PrinterMigrationDto(
        id = id,
        name = "DOTPR80",
        connectionMode = PrinterConnectionMode.USB,
        address = "4070:33054",
        encoding = PrinterMigrationDto.DEFAULT_ENCODING,
        charactersFontA = 48,
        charactersFontB = 64,
        appendLines = 2,
        canBeep = true,
        canCut = true,
        withDrawer = true,
        tasks = listOf(
            PrintTaskMigrationDto(
                id = 1000 * id,
                type = PrintTaskType.RECEIPT,
                header = "Testing header\nWith\nMultiple lines",
                logo = logos.random().toBase64(),
                copies = 1,
                beep = false,
                useFontB = false,
                filters = emptyList(),
                version = System.currentTimeMillis()
            )
        ),
        isDeleted = false,
        version = System.currentTimeMillis(),
    )

    private fun createCzFiscalizationConfiguration(id: Long, random: Random): CzFiscalizationConfiguration {
        val keystoreName = eetKeystores.keys.random()
        val keystore = eetKeystores[keystoreName] ?: throw IllegalStateException("No EET keystore found")
        val newPassphrase = "$migrationId$EET_SALT"
        return CzFiscalizationConfiguration(
            data = keystore.repackage("eet", newPassphrase).toBase64(newPassphrase),
            vatId = keystoreName,
            saleLocationId = random.nextLong(1, 99999),
            sellerId = null,
            printVatId = random.nextBoolean(),
            simplifiedMode = false,
            allowWithoutFiscalization = random.nextBoolean(),
            fiscalizeCashless = true,
            isEnabled = true
        )
    }

    private fun <T> randomList(numberOfRecords: Int, factory: (id: Long, random: Random) -> T) = lazy {
        val random = Random(seed)
        List(numberOfRecords) { id -> factory(id.toLong(), random) }
    }

    private fun randomLorem(words: Int) = List(words) { faker.lorem.words() }.joinToString(separator = " ")

    private fun Random.nextBigDecimal(min: Int = 0, max: Int = Int.MAX_VALUE, decimals: Int = 2): BigDecimal {
        return BigDecimal(nextLong(min.toLong(), max.toLong()) / 10.0.pow(decimals))
    }

    private fun Random.nextCompanyId() =
        nextInt(50000, 1000000).toString()

    private fun Random.nextVatId(countryCode: String = "CZ") =
        countryCode + nextLong(1_000_000_000, 9_999_999_999).toString()

    private fun <T> Random.valueOrDefault(occurence: Int, defaultValue: T, getValue: () -> T): T =
        if (nextInt() % occurence == 0) getValue() else defaultValue

    private fun <T> Random.valueOrNull(
        occurence: Int,
        getValue: () -> T
    ) =
        valueOrDefault(occurence, null, getValue)

    private fun <T> ifOrNull(condition: Boolean, getValue: () -> T) =
        if (condition) getValue() else null

    private fun BigDecimal.canonical(decimals: Int) {
        this.setScale(decimals, RoundingMode.HALF_DOWN)
    }

    private fun ByteArray.toBase64() = Base64.getEncoder().encode(this).let { String(it, Charsets.UTF_8) }

    companion object {
        val CURRENCIES = mapOf(
            "EUR" to 26.34,
            "USD" to 18.34,
            "CHF" to 23.23,
            "PLN" to 5.34,
            "JPY" to 0.23,
            "SEK" to 2.58,
            "HUF" to 0.072,
        )

        val EET_SALT = "DEMO"
    }
}
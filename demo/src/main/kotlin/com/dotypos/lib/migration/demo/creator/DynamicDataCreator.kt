package com.dotypos.lib.migration.demo.creator

import com.dotypos.lib.migration.demo.Configuration
import com.dotypos.lib.migration.dto.CloudMigrationDto
import com.dotypos.lib.migration.dto.PosMigrationDto
import com.dotypos.lib.migration.dto.config.*
import com.dotypos.lib.migration.dto.entity.*
import com.dotypos.lib.migration.dto.enumerate.*
import com.dotypos.lib.migration.dto.enumerate.permission.EmployeeMobileWaiterPermission
import com.dotypos.lib.migration.dto.enumerate.permission.EmployeePosPermission
import com.dotypos.lib.migration.dto.enumerate.permission.EmployeeStockPermission
import com.dotypos.lib.migration.extension.sha1hex
import com.dotypos.lib.migration.util.KeystoreUtil.repackage
import com.dotypos.lib.migration.util.KeystoreUtil.toBase64
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.create
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.*
import kotlin.math.pow
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Configurable dynamic data creator
 * WIP
 */
class DynamicDataCreator(
    private val config: Configuration,
    private val seed: Long,
    private val employees: Int = 20,
    private val products: Int = 30_000,
    private val ingredients: Int = products / 3,
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
    private val posStockTransactions: Int = 1,
    private val posStockOperations: Int = 100,
) : PosDataCreator, CloudDataCreator {

    private val fakerConfig = FakerConfig.builder().create {
        random = java.util.Random(seed)
    }
    private val faker = Faker(fakerConfig)

    // POS DATA
    private val migrationId = "DEMO$${Date()}"
    private val employeesList by randomList(employees, ::createEmployee)
    private val categoryList by randomList(categories, ::createCategory)
    private val productList by randomList(products, ::createProduct)
    private val ingredientList by lazy { createIngredients() }
    private val discountGroupList by randomList(discountGroups, ::createDiscountGroup)
    private val customerList by randomList(customers, ::createCustomer)
    private val courseList by randomList(courses, ::createCourse)
    private val tablePageList by randomList(tablePages, ::createTablePage)
    private val tableList by randomList(tables, ::createTable)
    private val warehouseList by randomList(warehouses, ::createWarehouse) { index, _ -> index.toLong() + 1 }
    private val supplierList by randomList(suppliers, ::createSupplier)
    private val printerList by randomList(printers, ::createPrinter)
    private val posStockTransactionsList by randomList(posStockTransactions, ::createTransaction)
    private val posStockOperationsList by randomList(posStockOperations, ::createOperation)

    private val czFiscalizationConfigurations by randomList(1, ::createCzFiscalizationConfiguration)

    override fun createPosData(): PosMigrationDto {
        val baseData = EmptyDemoDataCreator.createPosData()
        val random = Random(seed)

        return baseData.copy(
            metadata = PosMigrationDto.Metadata(
                migrationId = migrationId,
                created = Date(System.currentTimeMillis() - 1L),
                name = config.name,
                email = config.email,
                phone = config.phone,
                licenseKey = config.licenseKey,
                companyInfo = PosMigrationDto.CompanyInfo(
                    companyId = "12345678",
                    vatId = "CZ12345678",
                    name = faker.company.name(),
                    address = listOf(faker.address.streetAddress()),
                    city = faker.address.city(),
                    zip = faker.address.postcode(),
                ),
                pos = PosMigrationDto.PosMetadata(
                    id = "${random.nextLong()}/${random.nextLong()}",
                ),
            ),
            posConfiguration = baseData.posConfiguration.copy(
                currency = "PLN",
                alternativeCurrency = AlternativeCurrencyConfiguration(
                    currency = "CZK",
                    returnInPrimary = false,
                    roundingDecimals = 1,
                    exchangeRateAmount = BigDecimal.ONE,
                    exchangeRate = BigDecimal("3")
                ),
                czFiscalizationConfigurations = czFiscalizationConfigurations,
                documentNumbering = PosConfigurationDto.DocumentNumberingConfiguration(
                    receiptFormat = "%Y%m@6",
                    receiptLastNumber = "202103000123",
                    invoiceFormat = "I%Y%m@6",
                    invoiceLastNumber = "I202103000123",
                    cancellationInvoiceFormat = "CI%Y%m@6",
                    cancellationInvoiceLastNumber = "CI202103000123",
                ),
                orderNumberPrefix = faker.food.dish().substring(0, random.nextInt(1..4)),
                prefillValuesOnRegisterOpen = true,
                reportingEmails = setOf("example@example.com", "test@example.com"),
                display = DisplayConfiguration(
                    defaultScreen = DisplayConfiguration.DefaultScreen.TABLE_MAP,
                    saleScreen = DisplayConfiguration.SaleScreenConfiguration(
                        includeHiddenProductsInSearch = true,
                        displayPluButton = true,
                    ),
                ),
                cashDrawer = CashDrawerConfiguration(
                    showManualButton = true,
                ),
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
            stockTransactions = posStockTransactionsList,
            stockOperations = posStockOperationsList,
        )
    }

    override fun createCloudData(): CloudMigrationDto {
        val random = Random(seed)
        val baseData = EmptyDemoDataCreator.createCloudData()

        val documentList = mutableListOf<DocumentMigrationDto>()
        var created = Calendar.getInstance()
            .apply { set(Calendar.YEAR, 2018) }
            .timeInMillis

        val sellerId = null // TODO: Add seller support
        val employeeId = employeesList.random(random).id
        val customerId = random.valueOrNull(10) { customerList.random().id }

        val moneyOperations = mutableListOf<MoneyOperationMigrationDto>()
        val stockTransactions = List(random.nextInt(3, 10)) { pos ->
            StockTransactionMigrationDto(
                id = pos.toLong(),
                warehouseId = warehouseList.first().id,
                supplierId = null,
                invoiceNumber = "ST-${random.nextLong().toString()}",
                type = StockTransactionMigrationDto.Type.STOCK_TAKING,
                note = "Initial state",
                created = Date(),
                version = System.currentTimeMillis()
            )
        }
        val stockOperations = mutableListOf<StockOperationMigrationDto>()

        val stockQuantityStatus = mutableMapOf<Long, BigDecimal>()
        productList
            .filter { it.stockDeducted }
            .forEachIndexed { index, product ->
                val initialQuantity = product.packaging * random.nextLong(0, 1000).toBigDecimal()
                val purchasePrice = random.nextBigDecimal(
                    (product.unitPriceWithVat * BigDecimal("0.2")).toInt(),
                    product.unitPriceWithVat.toInt()
                )
                val stockTransaction = stockTransactions.random(random)
                stockOperations += StockOperationMigrationDto(
                    id = index.toLong(),
                    stockTransactionId = stockTransaction.id,
                    productId = product.id,
                    warehouseId = warehouseList.first().id,
                    employeeId = employeesList.random(random).id,
                    sellerId = null,
                    documentId = null,
                    quantityStatus = initialQuantity,
                    quantity = initialQuantity,
                    measurementUnit = product.measurementUnit,
                    purchasePrice = purchasePrice,
                    avgPurchasePrice = purchasePrice,
                    currency = "CZK",
                    note = stockTransaction.note,
                    type = StockOperationMigrationDto.Type.STOCK_TAKING,
                    version = System.currentTimeMillis(),
                )
                stockQuantityStatus[product.id] = initialQuantity
            }

        repeat(documents) { pos ->
            val documentId = pos.toLong()
            val foreignCurrencyCode = random.valueOrNull(20) { CURRENCIES.keys.random(random) }

            val itemsMathContext = MathContext(2, RoundingMode.HALF_UP)

            // Items
            val items = List(random.nextInt(1, 20)) { itemPos ->
                val itemId = documentId * 1000L + itemPos
                val product = productList.random(random)

                val baseUnitPriceWithVat = product.unitPriceWithVat
                val vatMultiplier =
                    product.vatRate?.multiply(BigDecimal("0.01"))?.plus(BigDecimal.ONE) ?: BigDecimal.ONE
                val baseUnitPriceWithoutVat = baseUnitPriceWithVat
                    .divide(vatMultiplier, MathContext.DECIMAL128)

                val discountPercent = random.valueOrDefault(3, BigDecimal.ZERO) {
                    random.nextBigDecimal(0, 1, 3)
                }
                val discountPercentMultiplier = (BigDecimal("100") - discountPercent) * BigDecimal("0.01")
                val quantity = random.nextLong(1, 5).toBigDecimal()

                val totalPriceWithVat = (baseUnitPriceWithVat * quantity * discountPercentMultiplier)
                    .round(itemsMathContext)
                val totalPriceWithoutVat = totalPriceWithVat.divide(vatMultiplier, itemsMathContext)

                // Ingredients
                stockOperations += ingredientList
                    .filter { it.parentProductId == product.id }
                    .mapIndexed { index, ingredientMigrationDto ->
                        val ingredientProduct =
                            productList.first { it.id == ingredientMigrationDto.ingredientProductId }
                        // Modify status
                        val status =
                            (stockQuantityStatus[ingredientMigrationDto.ingredientProductId] ?: BigDecimal.ZERO)
                                .minus(ingredientMigrationDto.quantity)
                        stockQuantityStatus[ingredientMigrationDto.ingredientProductId] = status

                        StockOperationMigrationDto(
                            id = 100_000_000L + itemId * 10_000L + index,
                            stockTransactionId = null,
                            productId = ingredientMigrationDto.ingredientProductId,
                            warehouseId = warehouseList.first().id,
                            employeeId = employeeId,
                            sellerId = sellerId,
                            documentId = documentId,
                            quantity = ingredientMigrationDto.quantity,
                            quantityStatus = status,
                            measurementUnit = ingredientProduct.measurementUnit,
                            purchasePrice = null,
                            avgPurchasePrice = null,
                            currency = "CZK",
                            note = "",
                            type = StockOperationMigrationDto.Type.SALE,
                            version = System.currentTimeMillis(),
                        )
                    }

                DocumentItemMigrationDto(
                    id = itemId,
                    productId = product.id,
                    categoryId = product.categoryId,
                    relatedDocumentItemId = null,
                    courseId = random.valueOrNull(5) {
                        courseList.randomOrNull(random)?.id
                    },
                    type = DocumentItemMigrationDto.Type.COMMON, // TODO: Do more type of items
                    name = product.name,
                    printName = product.printName,
                    subtitle = product.subtitle,
                    note = product.note,
                    hexColor = product.hexColor,
                    packaging = product.packaging,
                    measurementUnit = product.measurementUnit,
                    ean = product.ean,
                    quantity = random.nextLong(1, 5).toBigDecimal(),
                    unitPurchasePrice = random.valueOrNull(2) {
                        random.nextBigDecimal(
                            (product.unitPriceWithVat * BigDecimal("0.2")).toInt(),
                            product.unitPriceWithVat.toInt()
                        )
                    },
                    baseUnitPriceWithoutVat = baseUnitPriceWithoutVat,
                    baseUnitPriceWithVat = baseUnitPriceWithVat,
                    billedUnitPriceWithoutVat = totalPriceWithoutVat.divide(quantity, MathContext.DECIMAL128),
                    billedUnitPriceWithVat = totalPriceWithVat.divide(quantity, MathContext.DECIMAL128),
                    totalPriceWithoutVat = totalPriceWithoutVat,
                    totalPriceWithVat = totalPriceWithVat,
                    vatRate = product.vatRate,
                    discountPercent = discountPercent,
                    points = if (customerId != null) product.points * quantity else BigDecimal.ZERO,
                    priceInPoints = null,
                    stockDeducted = product.stockDeducted,
                    tags = product.tags,
                    version = System.currentTimeMillis(),
                )
            }

            val total = items.sumOf { it.totalPriceWithVat }
            val totalPoints = items.sumOf { it.points }

            // Money operations
            val paid = random.nextBoolean()
            if (paid) {
                val paymentMethod = PaymentMethod.values().random(random)
                moneyOperations += MoneyOperationMigrationDto(
                    id = documentId,
                    sellerId = sellerId,
                    employeeId = employeeId,
                    documentId = documentId,
                    type = MoneyOperationMigrationDto.Type.SALE,
                    paymentMethod = paymentMethod,
                    primaryAmount = total,
                    currency = "CZK",
                    amount = total,
                    exchangeRate = BigDecimal.ONE,
                    note = "",
                    created = Date(),
                    cardPaymentData = if (paymentMethod == PaymentMethod.CARD) {
                        MoneyOperationMigrationDto.CardPaymentData(
                            transactionCode = random.nextLong().toString()
                        )
                    } else {
                        null
                    },
                    tags = emptyList(),
                    version = System.currentTimeMillis(),
                )
            }

            documentList += DocumentMigrationDto(
                documentId,
                type = DocumentMigrationDto.Type.RECEIPT,
                cancellationType = DocumentMigrationDto.CancellationType.NONE,
                relatedDocumentId = null,
                tableId = random.valueOrNull(2) { tableList.randomOrNull()?.id },
                created = Date(created),
                documentNumber = "ADFS-${pos}",
                issueDate = Date(created + random.nextLong(2 * 60_000, 30 * 60_000)),
                customerId = customerId,
                employeeId = employeeId,
                location = random.valueOrNull(2) {
                    DocumentMigrationDto.Location(
                        date = Date(created),
                        accuracy = random.nextFloat() * 5,
                        latitude = random.nextDouble() * 180 - 90,
                        longitude = random.nextDouble() * 360 - 180,
                    )
                },
                note = random.valueOrDefault(4, "") { randomLorem(random.nextInt(3, 25)) },
                items = items,
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
                sellerId = sellerId,
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
            migrationResultData = "1|${config.cloudId}|${config.branchId}",
            documents = documentList,
            moneyOperations = moneyOperations,
            stockTransactions = stockTransactions,
            stockOperations = stockOperations,
        )
    }

    private fun createEmployee(id: Long, random: Random): EmployeeMigrationDto {
        val name = faker.name.name()
        val pin = if (id == 0L) {
            EmployeeMigrationDto.PinWrapper(
                type = EmployeeMigrationDto.PinWrapper.Type.PLAINTEXT,
                data = "0000",
            )
            EmployeeMigrationDto.PinWrapper(
                type = EmployeeMigrationDto.PinWrapper.Type.MARKEETA_HASH,
                data = "\$b59173c0\$sha-256\$0fe79e295a2b8da9f8686dc3b348ff1605b57834f0cff0c83f9c2cd9e98648b0",
            )
        } else {
            random.valueOrNull(2) {
                EmployeeMigrationDto.PinWrapper(
                    type = EmployeeMigrationDto.PinWrapper.Type.PLAINTEXT,
                    data = random.nextInt(1111, 9999).toString(),
                )
            }
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
            posPermissions = if (id == 0L) {
                EmployeePosPermission.values().toSet()
            } else {
                EmployeePosPermission.values().filter { random.nextBoolean() }.toSet()
            },
            stockPermissions = if (id == 0L) {
                EmployeeStockPermission.values().toSet()
            } else {
                EmployeeStockPermission.values().filter { random.nextBoolean() }.toSet()
            },
            mobileWaiterPermissions =
            if (id == 0L) {
                EmployeeMobileWaiterPermission.values().toSet()
            } else {
                EmployeeMobileWaiterPermission.values().filter { random.nextBoolean() }.toSet()
            },
            isEnabled = if (id == 0L) true else random.valueOrDefault(5, true) { false },
            isDeleted = if (id == 0L) false else random.valueOrDefault(20, false) { true },
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
        unitPriceWithVat = random.nextBigDecimal(max = 30_000, decimals = 0) + BigDecimal("0.90"),
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
        val ingredientProductsCount = if (ingredients == 0) {
            0
        } else {
            random.nextInt(maxOf(products / ingredients, 1), minOf(ingredients, products))
        }
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
            country = faker.address.countryCode(),
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
            PrinterConnectionMode.INTERNAL_LANDI_A8,
            -> "local"
        }
        val largePrinter = when (connectionMode) {
            PrinterConnectionMode.INTERNAL_SUNMI_V1,
            PrinterConnectionMode.INTERNAL_SUNMI_V2,
            PrinterConnectionMode.INTERNAL_LANDI_A8,
            -> false
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

    private fun createTransaction(id: Long, random: Random): StockTransactionMigrationDto {
        return StockTransactionMigrationDto(
            id = id,
            warehouseId = warehouseList.random(random).id,
            supplierId = supplierList.random(random).id,
            invoiceNumber = null,
            type = StockTransactionMigrationDto.Type.STOCK_TAKING,
            note = "",
            created = Date(),
            version = System.currentTimeMillis(),
        )
    }

    private fun createOperation(id: Long, random: Random): StockOperationMigrationDto {
        val transaction = posStockTransactionsList.random(random)
        val product = productList.random(random)
        return StockOperationMigrationDto(
            id = id,
            stockTransactionId = transaction.id,
            productId = product.id,
            warehouseId = transaction.warehouseId,
            employeeId = employeesList.random(random).id,
            sellerId = null, // TODO
            documentId = null,
            quantity = random.nextBigDecimal(0, 100),
            quantityStatus = random.nextBigDecimal(3, 40),
            measurementUnit = product.measurementUnit,
            purchasePrice = product.unitPriceWithVat * BigDecimal("0.8"),
            avgPurchasePrice = product.unitPriceWithVat * BigDecimal("0.8"),
            currency = "CZK",
            note = random.valueOrNull(occurence = 4) {
                randomLorem(random.nextInt(4, 10))
            } ?: "",
            type = StockOperationMigrationDto.Type.STOCK_TAKING,
            version = System.currentTimeMillis(),
        )
    }

    private fun createCzFiscalizationConfiguration(id: Long, random: Random): CzFiscalizationConfiguration {
        val keystoreName = eetKeystores.keys.random()
        val keystore = eetKeystores[keystoreName] ?: throw IllegalStateException("No EET keystore found")
        val newPassphrase =
            "$migrationId$EET_SALT".sha1hex() ?: throw IllegalStateException("Can't create hash of password")
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

    private fun <T> randomList(
        numberOfRecords: Int,
        factory: (id: Long, random: Random) -> T,
        createId: (index: Int, random: Random) -> Long = { index, _ -> index.toLong() },
    ) = lazy {
        val random = Random(seed)
        List(numberOfRecords) { index ->
            factory(createId(index, random), random)
        }
    }

    private fun randomLorem(words: Int) = List(words) { faker.lorem.words() }.joinToString(separator = " ")

    private fun Random.nextBigDecimal(min: Int = 0, max: Int = Int.MAX_VALUE, decimals: Int = 2): BigDecimal {
        if (min >= max) {
            return BigDecimal(min)
        }
        return BigDecimal(nextLong(min.toLong(), max.toLong()) / 10.0.pow(decimals))
    }

    private fun Random.nextCompanyId() =
        nextInt(50000, 1000000).toString()

    private fun Random.nextVatId(countryCode: String = "CZ") =
        countryCode + nextLong(1_000_000_000, 9_999_999_999).toString()

    private fun <T> Random.valueOrDefault(occurence: Int, defaultValue: T, getValue: () -> T): T =
        if (nextInt() % occurence == 0) getValue() else defaultValue

    private fun <T> Random.valueOrNull(occurence: Int, getValue: () -> T) = valueOrDefault(occurence, null, getValue)

    private fun <T> ifOrNull(condition: Boolean, getValue: () -> T) =
        if (condition) getValue() else null

    private fun BigDecimal.canonical(decimals: Int) {
        this.setScale(decimals, RoundingMode.HALF_DOWN)
    }

    private fun ByteArray.toBase64() =
        Base64.getEncoder().encodeToString(this)

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
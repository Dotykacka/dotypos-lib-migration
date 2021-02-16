package com.dotypos.lib.migration.demo.creator

import com.dotypos.lib.migration.dto.PosMigrationDto
import com.dotypos.lib.migration.dto.config.PosConfigurationDto
import com.dotypos.lib.migration.dto.enumerate.PaymentMethod
import java.util.*

object EmptyDemoDataCreator : PosDataCreator {
    override fun createPosData() = PosMigrationDto(
        metadata = PosMigrationDto.Metadata(
            migrationId = "EMPTY-min10",
            created = Date(System.currentTimeMillis() - 1L),
            email = null,
            licenseKey = null,
            pos = PosMigrationDto.PosMetadata(
                id = "EMPTY",
            ),
        ),
        posConfiguration = PosConfigurationDto(
            name = "Empty cash register",
            mode = PosConfigurationDto.Mode.CATALOG_DEFAULT,
            country = "CZ",
            currency = "CZK",
            rounding = PosConfigurationDto.RoundingConfiguration(
                displayed = 2,
                items = 2,
                total = 0,
            ),
            negativePrices = true,
            negativeAmount = true,
            groupItems = true,
            quickPayMode = false,
            paymentMethods = setOf(PaymentMethod.CASH),
            disableSaleBellowPurchasePrice = false,
            useQrCodeScanner = false,
            sendReceiptsByEmail = false,
            documentNumbering = PosConfigurationDto.DocumentNumberingConfiguration(
                receiptFormat = "%Y@6",
                receiptLastNumber = "2020@000000",
                invoiceFormat = null,
                invoiceLastNumber = null,
                cancellationInvoiceFormat = null,
                cancellationInvoiceLastNumber = null
            ),
            czFiscalizationConfigurations = emptyList(),
            defaultWarehouseId = 0
        ),
        employees = emptySet(),
        sellers = emptySet(),
        courses = emptyList(),
        categories = emptyList(),
        products = emptyList(),
        ingredients = emptyList(),
        customerDiscountGroups = emptyList(),
        customers = emptyList(),
        tablePages = emptyList(),
        tables = emptyList(),
        warehouses = emptyList(),
        suppliers = emptyList(),
        printers = emptyList(),
    )
}
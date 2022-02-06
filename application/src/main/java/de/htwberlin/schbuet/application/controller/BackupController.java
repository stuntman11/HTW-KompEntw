package de.htwberlin.schbuet.application.controller;

import de.htwberlin.schbuet.application.service.ExportService;

import java.io.IOException;

import de.htwberlin.schbuet.application.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/product")
@RestController
public class BackupController {

    private final ExportService exports;
    private final WarehouseService warehouse;

    public BackupController(ExportService exports, WarehouseService warehouse) {
        this.exports = exports;
        this.warehouse = warehouse;
    }

    @PostMapping(value = "/export")
    public void exportProductsToFile() throws IOException {
		exports.createProductsExportFile();
    }

    @PostMapping(value = "/import")
    public void importProductsFromFile()  {
		warehouse.importProducts();
    }
}

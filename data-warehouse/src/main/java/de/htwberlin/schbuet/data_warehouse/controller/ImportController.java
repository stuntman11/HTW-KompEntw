package de.htwberlin.schbuet.data_warehouse.controller;

import de.htwberlin.schbuet.data_warehouse.services.ImportService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportController {

    private final ImportService importService;

    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @GetMapping(value = "/import-products")
    @ApiResponse(description = "Manually trigger import products from csv file")
    public void importProductsFromFile() {
        importService.importProductsFromCsv();
    }
}

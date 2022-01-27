package de.htwberlin.schbuet.application.controller;

import de.htwberlin.schbuet.application.service.CsvService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExportController {

    private final CsvService csvService;

    public ExportController(CsvService csvService) {
        this.csvService = csvService;
    }

    @GetMapping(value = "/export-product")
    public void getAdditionalProductInfo() {
        csvService.exportCsvToFile();
    }
}

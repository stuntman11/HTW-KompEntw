package de.htwberlin.schbuet.application.controller;

import de.htwberlin.schbuet.application.service.CsvService;

import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/product")
@RestController
public class ExportController {

    private final CsvService csvService;

    public ExportController(CsvService csvService) {
        this.csvService = csvService;
    }

    @GetMapping(value = "/export")
    public void getAdditionalProductInfo() {
		csvService.exportCsvToFile();
    }
}

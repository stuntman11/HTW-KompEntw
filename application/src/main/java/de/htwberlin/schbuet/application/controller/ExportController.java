package de.htwberlin.schbuet.application.controller;

import de.htwberlin.schbuet.application.service.ExportService;

import java.io.IOException;

import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/product")
@RestController
public class ExportController {

    private final ExportService exports;

    public ExportController(ExportService exports) {
        this.exports = exports;
    }

    @PostMapping(value = "/export")
    public void exportProductsToFile() throws IOException {
		exports.createProductsExportFile();
    }
}

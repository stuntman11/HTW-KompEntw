package de.htwberlin.schbuet.application.controller;

import de.htwberlin.schbuet.application.service.CsvService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExportController {

    private final CsvService csvService;

    public ExportController(CsvService csvService) {
        this.csvService = csvService;
    }

    @RequestMapping(value = "export-product/export.csv", method = RequestMethod.GET, produces="text/csv")
    public String getAdditionalProductInfo() {
        return csvService.getAllProductAsCSV().toString();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String param = ex.getParameterName();
        return "required query parameter '" + param + "' is missing";
    }
}

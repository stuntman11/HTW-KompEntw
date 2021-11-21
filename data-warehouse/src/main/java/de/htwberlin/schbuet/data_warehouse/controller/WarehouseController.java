package de.htwberlin.schbuet.data_warehouse.controller;

import de.htwberlin.schbuet.data_warehouse.data.main.WarehouseItem;
import de.htwberlin.schbuet.data_warehouse.repos.WarehouseItemRepository;
import de.htwberlin.schbuet.data_warehouse.services.ImportWarehouseItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class WarehouseController {

    private final WarehouseItemRepository warehouseItemRepository;
    private final ImportWarehouseItemService importWarehouseItemService;

    public WarehouseController(WarehouseItemRepository warehouseItemRepository, ImportWarehouseItemService importWarehouseItemService) {
        this.warehouseItemRepository = warehouseItemRepository;
        this.importWarehouseItemService = importWarehouseItemService;
    }

    @GetMapping("/product-info/{productId}")
    public WarehouseItem getAdditionalProductInfo(@PathVariable UUID productId) {
        return warehouseItemRepository.findTop1AndProductId(productId);
    }

    @PostMapping("/product-info/")
    public UUID importWarehouseItemsFromCSV(@RequestParam("csvFile") MultipartFile csvFile) {
        return importWarehouseItemService.importWarehouseData(csvFile);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String param = ex.getParameterName();
        return "required query parameter '" + param + "' is missing";
    }
}

package de.htwberlin.schbuet.data_warehouse.controller;

import de.htwberlin.schbuet.data_warehouse.data.request.RequestWarehouseItem;
import de.htwberlin.schbuet.data_warehouse.data.main.WarehouseItem;
import de.htwberlin.schbuet.data_warehouse.services.WarehouseService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/product-info/{productId}")
    @ApiResponse(description = "Returns a warehouse item for the given UUID.")
    public WarehouseItem getAdditionalProductInfo(@PathVariable UUID productId) {
        return warehouseService.getWarehouseItemByUUID(productId);
    }

    @PostMapping("/product-info/")
    @ApiResponse(description = "Creates a new warehouse Item for a product. if there is already a item, it will be updated.")
    public UUID createWarehouseItem(@RequestBody RequestWarehouseItem body) {
        return warehouseService.createOrUpdateWarehouseItem(body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String param = ex.getParameterName();
        return "required query parameter '" + param + "' is missing";
    }
}

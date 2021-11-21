package de.htwberlin.schbuet.data_warehouse.controller;

import de.htwberlin.schbuet.data_warehouse.data.main.WarehouseItem;
import de.htwberlin.schbuet.data_warehouse.repos.WarehouseItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class WarehouseController {

    private final WarehouseItemRepository warehouseItemRepository;

    public WarehouseController(WarehouseItemRepository warehouseItemRepository) {
        this.warehouseItemRepository = warehouseItemRepository;
    }

    @GetMapping("/product-info/{productId}")
    public WarehouseItem getAdditionalProductInfo(@PathVariable UUID productId) {
        return warehouseItemRepository.findTop1AndProductId(productId);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String param = ex.getParameterName();
        return "required query parameter '" + param + "' is missing";
    }
}

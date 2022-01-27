package de.htwberlin.schbuet.data_warehouse.controller;

import de.htwberlin.schbuet.data_warehouse.data.body.BodyWarehouseItem;
import de.htwberlin.schbuet.data_warehouse.data.main.WarehouseItem;
import de.htwberlin.schbuet.data_warehouse.repos.WarehouseItemRepository;
import de.htwberlin.schbuet.data_warehouse.services.ImportWarehouseItemService;
import de.htwberlin.schbuet.data_warehouse.services.WarehouseService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
public class WarehouseController {

    private final WarehouseItemRepository warehouseItemRepository;
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseItemRepository warehouseItemRepository, WarehouseService warehouseService) {
        this.warehouseItemRepository = warehouseItemRepository;
        this.warehouseService = warehouseService;
    }

    @GetMapping("/product-info/{productId}")
    @ApiResponse(description = "Returns a warehouse item")
    public WarehouseItem getAdditionalProductInfo(@PathVariable UUID productId) {
        return warehouseItemRepository.findTop1ByProductId(productId);
    }

    @PostMapping("/product-info/")
    public UUID createWareHouseItem(@RequestParam("csvFile") BodyWarehouseItem body) {
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

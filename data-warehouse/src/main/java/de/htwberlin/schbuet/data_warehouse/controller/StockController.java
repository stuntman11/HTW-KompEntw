package de.htwberlin.schbuet.data_warehouse.controller;

import de.htwberlin.schbuet.data_warehouse.data.request.RequestStockItem;
import de.htwberlin.schbuet.data_warehouse.data.main.StockItem;
import de.htwberlin.schbuet.data_warehouse.services.WarehouseService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class StockController {

    private final WarehouseService warehouse;

    public StockController(WarehouseService warehouse) {
        this.warehouse = warehouse;
    }

    @GetMapping("/stock/{productId}")
    @ApiResponse(description = "Returns a stock item for the given UUID.")
    public StockItem getStockItem(@PathVariable UUID productId) {
        return warehouse.getStockItemByUUID(productId);
    }

    @PostMapping("/stock/")
    @ApiResponse(description = "Creates a new stock item for a product. if there is already a item, it will be updated.")
    public UUID createStockItem(@RequestBody RequestStockItem body) {
        return warehouse.createOrUpdateStockItem(body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String param = ex.getParameterName();
        return "required query parameter '" + param + "' is missing";
    }
}

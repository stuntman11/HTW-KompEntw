package de.htwberlin.schbuet.data_warehouse.controller;

import de.htwberlin.schbuet.data_warehouse.data.main.Product;
import de.htwberlin.schbuet.data_warehouse.services.ProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService products;

    public ProductController(ProductService products) {
        this.products = products;
    }

    @GetMapping(value = "/products")
    @ApiResponse(description = "Get all products saved from import file to check if data is valid")
    public List<Product> getAllProducts() {
        return products.getAllProducts();
    }
}

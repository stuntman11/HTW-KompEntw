package de.htwberlin.schbuet.application.controller;

import de.htwberlin.schbuet.application.data.response.ResponseBasicProduct;
import de.htwberlin.schbuet.application.data.response.ResponseFullProduct;
import de.htwberlin.schbuet.application.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public ResponseFullProduct getFullProductInfo(@PathVariable UUID uuid) {
        return productService.getDetailedProductInfo(uuid);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces="text/csv")
    public List<ResponseBasicProduct> getBasicProductList() {
        return productService.getAllProducts();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String param = ex.getParameterName();
        return "required query parameter '" + param + "' is missing";
    }
}

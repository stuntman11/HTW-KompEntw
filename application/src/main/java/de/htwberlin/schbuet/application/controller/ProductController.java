package de.htwberlin.schbuet.application.controller;

import com.sun.istack.NotNull;
import de.htwberlin.schbuet.application.data.request.RequestProduct;
import de.htwberlin.schbuet.application.data.response.ResponseBasicProduct;
import de.htwberlin.schbuet.application.data.response.ResponseFullProduct;
import de.htwberlin.schbuet.application.errors.GeoLookupException;
import de.htwberlin.schbuet.application.errors.ProductNotFoundException;
import de.htwberlin.schbuet.application.errors.StockCreationFailedException;
import de.htwberlin.schbuet.application.errors.StockNotFoundException;
import de.htwberlin.schbuet.application.errors.TaxCouldNotBeCalculatedException;
import de.htwberlin.schbuet.application.service.ProductService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/product")
@Validated
public class ProductController {

    private final ProductService products;

    public ProductController(ProductService products) {
        this.products = products;
    }

    @GetMapping(value = "/")
    public List<ResponseBasicProduct> getBasicProductList() {
        return products.getAllProducts();
    }

    @GetMapping(value = "/{uuid}")
    public ResponseFullProduct getFullProductInfo(@PathVariable @NotNull UUID uuid) throws ProductNotFoundException, TaxCouldNotBeCalculatedException, StockNotFoundException, GeoLookupException {
        return products.getDetailedProductInfo(uuid);
    }

    //This functionality is for demo purposes only. For productive use, strong authentication must be implemented.
    @DeleteMapping(value = "/{uuid}")
    public void deleteProduct(@PathVariable @NotNull UUID uuid) throws ProductNotFoundException {
        products.deleteProduct(uuid);
    }

    //This functionality is for demo purposes only. For productive use, strong authentication must be implemented.
    @PostMapping(value = "/")
    public UUID createProduct(@Valid @RequestBody RequestProduct body) throws StockCreationFailedException {
        return products.createProduct(body);
    }

    //This functionality is for demo purposes only. For productive use, strong authentication must be implemented.
    @PutMapping(value = "/{uuid}")
    public void updateProduct(@Valid @RequestBody RequestProduct body, @PathVariable @NotNull UUID uuid) throws ProductNotFoundException, StockCreationFailedException {
        products.updateProduct(uuid, body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String param = ex.getParameterName();
        return "required query parameter '" + param + "' is missing";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolation(ConstraintViolationException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

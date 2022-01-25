package de.htwberlin.schbuet.application.service;

import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.data.response.ResponseProduct;
import de.htwberlin.schbuet.application.exceptions.GeoServiceException;
import de.htwberlin.schbuet.application.exceptions.ResourceNotFoundException;
import de.htwberlin.schbuet.application.exceptions.TaxCouldNotBeCalculatedException;
import de.htwberlin.schbuet.application.exceptions.WarehouseResourceNotFoundException;
import de.htwberlin.schbuet.application.repos.ProductRepository;
import de.htwberlin.schbuet.application.service.geo.GeoCoords;
import de.htwberlin.schbuet.application.service.geo.GoogleMapsGeoService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {
    private final InternalCalculatorService calculatorService;
    private final WarehouseService warehouseService;
    private final GoogleMapsGeoService googleMapsGeoService;
    private final ProductRepository productRepository;

    public ProductService(InternalCalculatorService calculatorService, WarehouseService warehouseService, GoogleMapsGeoService googleMapsGeoService, ProductRepository productRepository) {
        this.calculatorService = calculatorService;
        this.warehouseService = warehouseService;
        this.googleMapsGeoService = googleMapsGeoService;
        this.productRepository = productRepository;
    }

    @SneakyThrows()
    public ResponseProduct getDetailedProductInfo(UUID uuid) {
        var product = productRepository.findById(uuid);
        if (product == null)
            throw new ResourceNotFoundException(uuid);
        return getResponseProduct(product);
    }

    @SneakyThrows()
    private ResponseProduct getResponseProduct(Product product) {
        var tax = calculatorService.getTaxForPrice(product.getPriceInCents());
        if (tax == null)
            throw new TaxCouldNotBeCalculatedException();

        var warehouse = warehouseService.getWarehouseInfoForProduct(product.getId());
        if (warehouse == null)
            throw new WarehouseResourceNotFoundException(product.getId());

        var geoCoordinates = new GeoCoords(warehouse.getLatitude(), warehouse.getLongitude());
        var address = googleMapsGeoService.getAddressFromCoords(geoCoordinates);

        return new ResponseProduct(product, tax, address);
    }
}

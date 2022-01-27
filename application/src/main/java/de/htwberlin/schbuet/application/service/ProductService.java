package de.htwberlin.schbuet.application.service;

import de.htwberlin.schbuet.application.data.body.RequestProduct;
import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.data.response.ResponseBasicProduct;
import de.htwberlin.schbuet.application.data.response.ResponseFullProduct;
import de.htwberlin.schbuet.application.exceptions.ResourceNotFoundException;
import de.htwberlin.schbuet.application.exceptions.TaxCouldNotBeCalculatedException;
import de.htwberlin.schbuet.application.exceptions.WarehouseResourceNotFoundException;
import de.htwberlin.schbuet.application.repos.ProductRepository;
import de.htwberlin.schbuet.application.service.geo.GeoCoords;
import de.htwberlin.schbuet.application.service.geo.GoogleMapsGeoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    public ResponseFullProduct getDetailedProductInfo(UUID uuid) {
        var product = productRepository.findById(uuid);
        if (product == null) {
            log.warn("could not find a product with id " + uuid);
            throw new ResourceNotFoundException(uuid);
        }

        return getFullProduct(product);
    }

    public List<ResponseBasicProduct> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ResponseBasicProduct::new)
                .collect(Collectors.toList());
    }

    public UUID createProduct(RequestProduct requestProduct) {
        var product = Product.builder()
                .name(requestProduct.getName())
                .description(requestProduct.getDescription())
                .category(requestProduct.getCategory())
                .itemNumber(requestProduct.getItemNumber())
                .priceInCents(requestProduct.getPriceInCents())
                .yearOfProduction(requestProduct.getYearOfProduction())
                .createdDate(Calendar.getInstance().getTime())
                .build();

        var savedProduct = productRepository.save(product);
        log.info("New product was created. ID:" + savedProduct.getId());
        return savedProduct.getId();
    }

    @SneakyThrows()
    public void updateProduct(RequestProduct requestProduct, UUID uuid) {
        var product = productRepository.findById(uuid);
        if (product == null) {
            log.warn("could not find a product with id " + uuid);
            throw new ResourceNotFoundException(uuid);
        }

        product.setCategory(requestProduct.getCategory());
        product.setDescription(requestProduct.getDescription());
        product.setYearOfProduction(requestProduct.getYearOfProduction());
        product.setItemNumber(requestProduct.getItemNumber());
        product.setName(requestProduct.getName());
        product.setPriceInCents(requestProduct.getPriceInCents());

        productRepository.save(product);
        log.info("Product was updated. ID:" + product.getId());
    }

    @SneakyThrows()
    public void deleteProduct(UUID uuid) {
        var product = productRepository.findById(uuid);
        if (product == null) {
            log.warn("could not delete resource with uuid " + uuid);
            throw new ResourceNotFoundException(uuid);
        }

        productRepository.delete(product);
        log.info("Product was deleted. ID:" + uuid);
    }

    @SneakyThrows()
    private ResponseFullProduct getFullProduct(Product product) {
        var tax = calculatorService.getTaxForPrice(product.getPriceInCents());
        if (tax == null) {
            log.warn("could not get tax for price");
            throw new TaxCouldNotBeCalculatedException();
        }

        var warehouse = warehouseService.getWarehouseInfoForProduct(product.getId());
        if (warehouse == null) {
            log.warn("There is no warehouse item for the product with uuid " + product.getId());
            throw new WarehouseResourceNotFoundException(product.getId());
        }

        var geoCoordinates = new GeoCoords(warehouse.getLatitude(), warehouse.getLongitude());
        var address = googleMapsGeoService.getAddressFromCoords(geoCoordinates);

        return new ResponseFullProduct(product, tax, address);
    }
}

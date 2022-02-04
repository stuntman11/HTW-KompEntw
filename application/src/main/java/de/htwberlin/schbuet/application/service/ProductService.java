package de.htwberlin.schbuet.application.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.data.request.RequestProduct;
import de.htwberlin.schbuet.application.data.response.ResponseBasicProduct;
import de.htwberlin.schbuet.application.data.response.ResponseFullProduct;
import de.htwberlin.schbuet.application.errors.ResourceNotFoundException;
import de.htwberlin.schbuet.application.errors.TaxCouldNotBeCalculatedException;
import de.htwberlin.schbuet.application.errors.WarehouseResourceNotFoundException;
import de.htwberlin.schbuet.application.repos.ProductRepository;
import de.htwberlin.schbuet.application.service.geo.GeoCoords;
import de.htwberlin.schbuet.application.service.geo.GoogleMapsGeoService;
import de.htwberlin.schbuet.application.service.tax.InternalTaxService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
    private final InternalTaxService taxCalculator;
    private final WarehouseService warehouse;
    private final GoogleMapsGeoService geo;
    private final ProductRepository productRepository;
    
    public ProductService(InternalTaxService taxCalculator, WarehouseService warehouse, GoogleMapsGeoService geo, ProductRepository productRepository) {
        this.taxCalculator = taxCalculator;
        this.warehouse = warehouse;
        this.geo = geo;
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

        product = productRepository.save(product);
        warehouse.createStockItem(requestProduct, product.getId());
        log.info("New product was created. ID:" + product.getId());
        return product.getId();
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

        warehouse.createStockItem(requestProduct, product.getId());
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
        var tax = taxCalculator.getTaxForPrice(product.getPriceInCents());
        if (tax == null) {
            log.warn("could not get tax for price");
            throw new TaxCouldNotBeCalculatedException();
        }

        var stockItem = warehouse.getStockItemForProduct(product.getId());
        if (stockItem == null) {
            log.warn("There is no stock item for the product with uuid " + product.getId());
            throw new WarehouseResourceNotFoundException(product.getId());
        }

        var geoCoordinates = new GeoCoords(stockItem.getLatitude(), stockItem.getLongitude());
        var address = geo.getAddressFromCoords(geoCoordinates);

        return new ResponseFullProduct(product, tax, address, stockItem);
    }
}

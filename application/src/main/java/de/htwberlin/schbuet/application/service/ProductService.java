package de.htwberlin.schbuet.application.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.htwberlin.schbuet.application.data.geo.GeoCoords;
import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.data.request.RequestProduct;
import de.htwberlin.schbuet.application.data.response.ResponseBasicProduct;
import de.htwberlin.schbuet.application.data.response.ResponseFullProduct;
import de.htwberlin.schbuet.application.errors.GeoLookupException;
import de.htwberlin.schbuet.application.errors.ProductNotFoundException;
import de.htwberlin.schbuet.application.errors.StockCreationFailedException;
import de.htwberlin.schbuet.application.errors.StockNotFoundException;
import de.htwberlin.schbuet.application.errors.TaxCouldNotBeCalculatedException;
import de.htwberlin.schbuet.application.repos.ProductRepository;
import de.htwberlin.schbuet.application.service.geo.GeoService;
import de.htwberlin.schbuet.application.service.tax.InternalTaxService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
    private final InternalTaxService taxCalculator;
    private final WarehouseService warehouse;
    private final GeoService geo;
    private final ProductRepository productRepository;
    
    public ProductService(InternalTaxService taxCalculator, WarehouseService warehouse, GeoService geo, ProductRepository productRepository) {
        this.taxCalculator = taxCalculator;
        this.warehouse = warehouse;
        this.geo = geo;
        this.productRepository = productRepository;
    }

    public ResponseFullProduct getDetailedProductInfo(UUID productId) throws ProductNotFoundException, TaxCouldNotBeCalculatedException, StockNotFoundException, GeoLookupException {
        var product = getProduct(productId);
        var tax = taxCalculator.getTaxForPrice(product.getPriceInCents());
        var stock = warehouse.getStockForProduct(product.getId());
        var geoCoordinates = new GeoCoords(stock.getLatitude(), stock.getLongitude());
        var address = geo.getAddressFromCoords(geoCoordinates);
        return new ResponseFullProduct(product, tax, address, stock);
    }

    public List<ResponseBasicProduct> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ResponseBasicProduct::new)
                .collect(Collectors.toList());
    }

    public UUID createProduct(RequestProduct requestProduct) throws StockCreationFailedException {
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
        
        try {
    		warehouse.createStockItem(product.getId(), requestProduct);
            return product.getId();
        } catch (Exception e) {
        	productRepository.delete(product);
        	throw e;
        }
    }

    public void updateProduct(UUID productId, RequestProduct requestProduct) throws ProductNotFoundException, StockCreationFailedException {
        var product = getProduct(productId);
        product.setCategory(requestProduct.getCategory());
        product.setDescription(requestProduct.getDescription());
        product.setYearOfProduction(requestProduct.getYearOfProduction());
        product.setItemNumber(requestProduct.getItemNumber());
        product.setName(requestProduct.getName());
        product.setPriceInCents(requestProduct.getPriceInCents());

        productRepository.save(product);

        warehouse.createStockItem(product.getId(), requestProduct);
        log.info("Product with id: '{}' was updated", product.getId());
    }

    public void deleteProduct(UUID productId) throws ProductNotFoundException {
        var product = getProduct(productId);
        productRepository.delete(product);
        log.info("Product with id: '{}' was deleted", productId);
    }
    
    private Product getProduct(UUID productId) throws ProductNotFoundException {
        var product = productRepository.findById(productId);
        
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        return product;
    }
}

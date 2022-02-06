package de.htwberlin.schbuet.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import de.htwberlin.schbuet.application.data.geo.GeoAddress;
import de.htwberlin.schbuet.application.data.request.RequestProduct;
import de.htwberlin.schbuet.application.data.request.RequestTax;
import de.htwberlin.schbuet.application.data.response.ResponseStockItem;
import de.htwberlin.schbuet.application.errors.ProductNotFoundException;
import de.htwberlin.schbuet.application.repos.ProductRepository;
import de.htwberlin.schbuet.application.service.ProductService;
import de.htwberlin.schbuet.application.service.WarehouseService;
import de.htwberlin.schbuet.application.service.geo.GeoService;
import de.htwberlin.schbuet.application.service.tax.InternalTaxService;

@SpringBootTest
class ProductServiceTest {
	@MockBean InternalTaxService taxCalculator;
    @MockBean WarehouseService warehouse;
    @MockBean GeoService geo;
    @Autowired ProductRepository productRepository;
	ProductService products;
	
	final RequestProduct PRODUCT1 = new RequestProduct("Name1", "Desc", "Category", "IT-1234", null, 1000, 2022, 5, 2);
	final RequestProduct PRODUCT2 = new RequestProduct("Name2", "Desc2", "Category2", "IT-4321", null, 2000, 2021, 1, 7);
	final ResponseStockItem STOCK = new ResponseStockItem(null, null, 4, 2, 52.152, 23.632, null, null);
	final GeoAddress ADDRESS = new GeoAddress("Deutschland", "Berlin", "10178", "Alexanderplatz 1");
	final RequestTax TAX = new RequestTax(100, 19, 119, 0.19);
	
	@BeforeEach
	void setUp() {
		products = new ProductService(taxCalculator, warehouse, geo, productRepository);
		productRepository.deleteAll();
	}
	
	@Test
	void createdProductIsSavedInDatabase() {
		UUID productId = products.createProduct(PRODUCT1);
		
		var savedProduct = products.getProduct(productId);
        assertEquals(productId, savedProduct.getId());
        assertEquals("Name1", savedProduct.getName());
        assertEquals("Desc", savedProduct.getDescription());
        assertEquals("Category", savedProduct.getCategory());
        assertEquals("IT-1234", savedProduct.getItemNumber());
        assertEquals(1000, savedProduct.getPriceInCents());
        assertEquals(2022, savedProduct.getYearOfProduction());
	}
	
	@Test
	void updatedProductIsUpdatedInDatabase() {
		UUID productId = products.createProduct(PRODUCT1);
		
		products.updateProduct(productId, PRODUCT2);
		
		var savedProduct = products.getProduct(productId);
        assertEquals(productId, savedProduct.getId());
        assertEquals("Name2", savedProduct.getName());
        assertEquals("Desc2", savedProduct.getDescription());
        assertEquals("Category2", savedProduct.getCategory());
        assertEquals("IT-4321", savedProduct.getItemNumber());
        assertEquals(2000, savedProduct.getPriceInCents());
        assertEquals(2021, savedProduct.getYearOfProduction());
	}
	
	@Test
	void deletedProductIsRemovedFromDatabase() {
		var productId = products.createProduct(PRODUCT1);

		products.deleteProduct(productId);

		assertThrows(ProductNotFoundException.class, () -> {
			products.getProduct(productId);
		});
	}
	
	@Test
	void listsAllProductsInDatabase() {
		var productId1 = products.createProduct(PRODUCT1);
		var productId2 = products.createProduct(PRODUCT2);
		
		var allProducts = products.getAllProducts();
		
		var savedIds = allProducts.stream().map(r -> r.getId()).collect(Collectors.toList());
		assertEquals(2, allProducts.size());
		assertTrue(savedIds.contains(productId1));
		assertTrue(savedIds.contains(productId2));
	}
	
	@Test
	void getDetailedProductInfoReturnsCombinedData() {
		var productId = products.createProduct(PRODUCT1);
		when(geo.getAddressFromCoords(any())).thenReturn(ADDRESS);
		when(taxCalculator.getTaxForPrice(anyInt())).thenReturn(TAX);
		when(warehouse.getStockForProduct(any())).thenReturn(STOCK);
		
		var fullProduct = products.getDetailedProductInfo(productId);

        assertEquals(productId, fullProduct.getId());
        assertEquals("Name1", fullProduct.getName());
        assertEquals("Desc", fullProduct.getDescription());
        assertEquals("Category", fullProduct.getCategory());
        assertEquals("IT-1234", fullProduct.getItemNumber());
        assertEquals(TAX, fullProduct.getPrice());
        assertEquals(2022, fullProduct.getYearOfProduction());
        assertEquals(ADDRESS, fullProduct.getAddress());
        assertEquals(4, fullProduct.getQuantity());
        assertEquals(2, fullProduct.getDeliveryTimeInDays());
	}
	
	@Test
	void createProductCreatesWarehouseStockItem() {
		var productId = products.createProduct(PRODUCT1);
		
		verify(warehouse).createStockItem(productId, PRODUCT1);
	}
}

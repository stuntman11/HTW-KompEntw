package de.htwberlin.schbuet.application.services;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.hamcrest.text.MatchesPattern.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

import de.htwberlin.schbuet.application.data.geo.GeoAddress;
import de.htwberlin.schbuet.application.data.geo.GeoCoords;
import de.htwberlin.schbuet.application.data.request.RequestProduct;
import de.htwberlin.schbuet.application.errors.StockCreationFailedException;
import de.htwberlin.schbuet.application.errors.StockNotFoundException;
import de.htwberlin.schbuet.application.service.WarehouseService;
import de.htwberlin.schbuet.application.service.geo.GeoService;

@SpringBootTest
class WarehouseServiceTest {
	@MockBean GeoService geo;
	WarehouseService warehouse;
	MockRestServiceServer warehouseRest;
	
	final GeoCoords COORDS = new GeoCoords(52.612, 6.541);
	final GeoAddress ADDRESS = new GeoAddress("Germany", "Berlin", "10178", "Alexanderplatz 1");
	final RequestProduct PRODUCT = new RequestProduct("Name", "Desc", "Category", "IT-1234", ADDRESS, 1000, 2022, 5, 2);

	@BeforeEach
	void setUp() {
		warehouse = new WarehouseService(geo);
		warehouseRest = MockRestServiceServer.createServer(warehouse.getRestTemplate());
		when(geo.getCoordsFromAddress(ADDRESS)).thenReturn(COORDS);
	}
	
	@Test
	void createStockItemPostsRestRequest() {
		warehouseRest.expect(ExpectedCount.once(), requestTo(matchesPattern(".*/stock/")))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.OK).body("dbadab80-d565-4856-84c8-b99ec752fe65"));
		
		warehouse.createStockItem(UUID.fromString("dfc28384-33a2-495f-94c9-fad2c205a43a"), PRODUCT);
		
		warehouseRest.verify();
	}
	
	@Test
	void createStockItemUsesGeoService() {
		warehouseRest.expect(ExpectedCount.once(), requestTo(matchesPattern(".*/stock/")))
				.andRespond(withStatus(HttpStatus.OK));
		
		warehouse.createStockItem(UUID.fromString("dfc28384-33a2-495f-94c9-fad2c205a43a"), PRODUCT);
		
		verify(geo).getCoordsFromAddress(ADDRESS);
	}
	
	@Test
	void createStockItemThrowsExceptionIfRequestFails() {
		warehouseRest.expect(ExpectedCount.once(), requestTo(matchesPattern(".*/stock/")))
				.andRespond(withStatus(HttpStatus.BAD_GATEWAY));
		
		assertThrows(StockCreationFailedException.class, () -> {
			warehouse.createStockItem(UUID.fromString("dfc28384-33a2-495f-94c9-fad2c205a43a"), PRODUCT);
		});
		
		warehouseRest.verify();
	}
	
	@Test
	void getStockForProductThrowsExceptionIfStockDoesNotExist() {
		warehouseRest.expect(ExpectedCount.once(), requestTo(matchesPattern(".*/stock/dfc28384-33a2-495f-94c9-fad2c205a43a")))
				.andRespond(withStatus(HttpStatus.OK).body("null"));
		
		assertThrows(StockNotFoundException.class, () -> {
			warehouse.getStockForProduct(UUID.fromString("dfc28384-33a2-495f-94c9-fad2c205a43a"));
		});
		
		warehouseRest.verify();
	}
	
	@Test
	void getStockForProductThrowsExceptionIfRequestFails() {
		warehouseRest.expect(ExpectedCount.once(), requestTo(matchesPattern(".*/stock/dfc28384-33a2-495f-94c9-fad2c205a43a")))
				.andRespond(withStatus(HttpStatus.BAD_GATEWAY));
		
		assertThrows(StockNotFoundException.class, () -> {
			warehouse.getStockForProduct(UUID.fromString("dfc28384-33a2-495f-94c9-fad2c205a43a"));
		});
		
		warehouseRest.verify();
	}
}

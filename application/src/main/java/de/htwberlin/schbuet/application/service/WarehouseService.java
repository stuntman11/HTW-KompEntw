package de.htwberlin.schbuet.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.htwberlin.schbuet.application.data.request.RequestProduct;
import de.htwberlin.schbuet.application.data.request.RequestStockItem;
import de.htwberlin.schbuet.application.data.response.ResponseStockItem;
import de.htwberlin.schbuet.application.errors.GeoLookupException;
import de.htwberlin.schbuet.application.service.geo.GeoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WarehouseService {
	
    private final GeoService geo;
    private final RestTemplate rest;

    public WarehouseService(GeoService geo) {
        this.geo = geo;
        this.rest = new RestTemplateBuilder()
        		.rootUri("http://localhost:8090")
        		.build();
    }

    @SneakyThrows
    public void createStockItem(RequestProduct requestProduct, UUID productId) {
    	try {
            var coordinates = geo.getCoordsFromAddress(requestProduct.getAddress());
            var requestStock = RequestStockItem.builder()
                    .productId(productId)
                    .quantity(requestProduct.getQuantity())
                    .deliveryTimeInDays(requestProduct.getDeliveryTimeInDays())
                    .latitude(coordinates.getLatitude())
                    .longitude(coordinates.getLongitude())
                    .build();
            
            this.postRequestStockItem(requestStock);
    	} catch (GeoLookupException e) {
            log.warn("could not get coordinates for address");
    	}
    }

    public ResponseStockItem getStockItemForProduct(UUID productId) {
        String productUrl = "/stock/" + productId.toString();
        return rest.getForObject(productUrl, ResponseStockItem.class);
    }

    private void postRequestStockItem(RequestStockItem requestStock) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        rest.postForEntity("/stock/", new HttpEntity<>(requestStock, headers), String.class);

        log.info("stock item was posted to warehouse");
    }
}

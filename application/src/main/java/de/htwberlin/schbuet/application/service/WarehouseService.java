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
import de.htwberlin.schbuet.application.data.request.RequestWarehouseItem;
import de.htwberlin.schbuet.application.data.response.ResponseWarehouseItem;
import de.htwberlin.schbuet.application.errors.GeoServiceException;
import de.htwberlin.schbuet.application.service.geo.GoogleMapsGeoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WarehouseService {

    private final GoogleMapsGeoService geo;
    private final RestTemplate rest;

    public WarehouseService(GoogleMapsGeoService geo) {
        this.geo = geo;
        
        this.rest = new RestTemplateBuilder()
        		.rootUri("http://localhost:8090")
        		.build();
    }

    @SneakyThrows
    public void exportWarehouseItem(RequestProduct requestProduct, UUID productID) {
    	try {
            var coordinates = geo.getCoordsFromAddress(requestProduct.getAddress());
            var requestWarehouse = RequestWarehouseItem.builder()
                    .productId(productID)
                    .quantity(requestProduct.getQuantity())
                    .deliveryTimeInDays(requestProduct.getDeliveryTimeInDays())
                    .latitude(coordinates.getLatitude())
                    .longitude(coordinates.getLongitude())
                    .build();
            
            this.postRequestWarehouseItem(requestWarehouse);
    	} catch (GeoServiceException e) {
            log.warn("could not get coordinates for address");
    	}
    }

    public ResponseWarehouseItem getWarehouseInfoForProduct(UUID productID) {
        String productUrl = "/product-info/" + productID.toString();
        return rest.getForObject(productUrl, ResponseWarehouseItem.class);
    }

    private void postRequestWarehouseItem(RequestWarehouseItem requestWarehouseItem) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        rest.postForEntity("/product-info/", new HttpEntity<>(requestWarehouseItem, headers), String.class);

        log.info("Warehouse item was posted to warehouse");
    }
}

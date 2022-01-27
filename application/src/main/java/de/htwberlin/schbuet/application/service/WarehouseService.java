package de.htwberlin.schbuet.application.service;

import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.data.request.RequestProduct;
import de.htwberlin.schbuet.application.data.request.RequestWarehouseItem;
import de.htwberlin.schbuet.application.data.response.ResponseWarehouseItem;
import de.htwberlin.schbuet.application.service.geo.GeoAddress;
import de.htwberlin.schbuet.application.service.geo.GoogleMapsGeoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class WarehouseService {

    private static final String API_URL = "http://localhost:8090/product-info";

    private final CsvService csvService;
    private final RestTemplate restTemplate;
    private final GoogleMapsGeoService googleMapsGeoService;

    public WarehouseService(CsvService csvService, RestTemplateBuilder restTemplateBuilder, GoogleMapsGeoService googleMapsGeoService) {
        this.csvService = csvService;
        this.restTemplate = restTemplateBuilder.rootUri(API_URL).build();
        this.googleMapsGeoService = googleMapsGeoService;
    }

    public void exportWarehouseItemAsCSV(UUID id, int quantity, int deliveryTimeInDays, Double latitude, Double longitude) {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("csvFile", csvService.getWarehouseExportItem(id, quantity, deliveryTimeInDays, latitude, longitude));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        log.info("Products were exported as CSV");
    }

    @SneakyThrows
    public void exportWarehouseItem(RequestProduct requestProduct, UUID productID) {
        var coordinates = googleMapsGeoService.getCoordsFromAddress(requestProduct.getAddress());
        if (coordinates != null) {
            var requestWarehouse = RequestWarehouseItem.builder()
                    .productId(productID)
                    .quantity(requestProduct.getQuantity())
                    .deliveryTimeInDays(requestProduct.getDeliveryTimeInDays())
                    .latitude(coordinates.getLatitude())
                    .longitude(coordinates.getLongitude())
                    .build();

            this.postRequestWarehouseItem(requestWarehouse);
        } else {
            log.warn("could not get coordinates for address");
        }
    }

    public ResponseWarehouseItem getWarehouseInfoForProduct(UUID productID) {
        String url = "/" + productID.toString();
        return restTemplate.getForObject(url, ResponseWarehouseItem.class);
    }

    private void postRequestWarehouseItem(RequestWarehouseItem requestWarehouseItem) {

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("productId", requestWarehouseItem.getProductId());
        map.add("quantity", requestWarehouseItem.getQuantity());
        map.add("deliveryTimeInDays", requestWarehouseItem.getDeliveryTimeInDays());
        map.add("latitude", requestWarehouseItem.getLatitude());
        map.add("longitude", requestWarehouseItem.getLongitude());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        log.info("Warehouse item was posted to warehouse");
    }

}

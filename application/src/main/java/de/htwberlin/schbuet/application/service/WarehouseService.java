package de.htwberlin.schbuet.application.service;

import de.htwberlin.schbuet.application.data.body.BodyWarehouseItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Slf4j
public class WarehouseService {

    private static final String API_URL = "http://localhost:8090/product-info";

    private final CsvService csvService;
    private final RestTemplate restTemplate;

    public WarehouseService(CsvService csvService, RestTemplateBuilder restTemplateBuilder) {
        this.csvService = csvService;
        this.restTemplate = restTemplateBuilder.rootUri(API_URL).build();
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

    public BodyWarehouseItem getWarehouseInfoForProduct(UUID productID) {
        String url = "/" + productID.toString();
        return restTemplate.getForObject(url, BodyWarehouseItem.class);
    }

}

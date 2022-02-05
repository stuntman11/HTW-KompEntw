package de.htwberlin.schbuet.data_warehouse.data.request;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestStockItem {
    private UUID productId;
    private int quantity;
    private int deliveryTimeInDays;
    private Double latitude;
    private Double longitude;
}

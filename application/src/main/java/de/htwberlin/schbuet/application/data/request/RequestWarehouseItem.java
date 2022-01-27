package de.htwberlin.schbuet.application.data.request;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RequestWarehouseItem {
    private UUID productId;
    private int quantity;
    private int deliveryTimeInDays;
    private Double latitude;
    private Double longitude;
}

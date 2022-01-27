package de.htwberlin.schbuet.application.data.body;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class RequestWarehouseItem {
    private UUID id;
    private UUID productId;
    private int quantity;
    private int deliveryTimeInDays;
    private Double latitude;
    private Double longitude;
    private Date dateCreated;
    private Date dateLastUpdate;
}

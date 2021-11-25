package de.htwberlin.schbuet.application.data.response;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ResponseWarehouseItem {
    private UUID id;

    private UUID productId;

    private int quantity;

    private int deliveryTimeInDays;

    private Double latitude;

    private Double longitude;

    private Date dateCreated;

    private Date dateLastUpdate;
}

package de.htwberlin.schbuet.application.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStockItem {
    private UUID id;
    private UUID productId;
    private int quantity;
    private int deliveryTimeInDays;
    private Double latitude;
    private Double longitude;
    private Date dateCreated;
    private Date dateLastUpdate;
}

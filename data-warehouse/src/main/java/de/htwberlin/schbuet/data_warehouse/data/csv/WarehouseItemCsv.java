package de.htwberlin.schbuet.data_warehouse.data.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.util.UUID;

@Data
public class WarehouseItemCsv {

    @CsvBindByPosition(position = 0)
    private String productId;

    @CsvBindByPosition(position = 1)
    private int quantity;

    @CsvBindByPosition(position = 2)
    private int deliveryTimeInDays;

    @CsvBindByPosition(position = 3)
    private Double latitude;

    @CsvBindByPosition(position = 4)
    private Double longitude;

    public UUID getIdAsUUID() {
        return UUID.fromString(productId);
    }
}

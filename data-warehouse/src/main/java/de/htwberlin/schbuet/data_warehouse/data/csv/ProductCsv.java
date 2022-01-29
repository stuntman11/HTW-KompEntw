package de.htwberlin.schbuet.data_warehouse.data.csv;

import java.util.UUID;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;

@Data
public class ProductCsv {

    @CsvBindByPosition(position = 0)
    private String id;

    @CsvBindByPosition(position = 1)
    private String name;

    @CsvBindByPosition(position = 2)
    private String description;

    @CsvBindByPosition(position = 3)
    private String category;

    @CsvBindByPosition(position = 4)
    private String itemNumber;

    @CsvBindByPosition(position = 5)
    private int priceInCents;

    @CsvBindByPosition(position = 6)
    private int yearOfProduction;

    public UUID getIdAsUUID() {
        return UUID.fromString(id);
    }
}

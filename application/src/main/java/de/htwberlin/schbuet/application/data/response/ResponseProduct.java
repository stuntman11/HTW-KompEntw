package de.htwberlin.schbuet.application.data.response;

import de.htwberlin.schbuet.application.data.body.BodyWarehouseItem;
import de.htwberlin.schbuet.application.data.body.BodyTax;
import de.htwberlin.schbuet.application.data.main.Product;
import de.htwberlin.schbuet.application.service.geo.GeoAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProduct {

    private UUID id;
    private String name;
    private String description;
    private String category;
    private String itemNumber;
    private BodyTax price;
    private int yearOfProduction;
    private Date createdDate;
    private GeoAddress address;

    public ResponseProduct(Product product, BodyTax bodyTax, GeoAddress geoAddress) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.itemNumber = product.getItemNumber();
        this.price = bodyTax;
        this.yearOfProduction = product.getYearOfProduction();
        this.createdDate = product.getCreatedDate();
        this.address = geoAddress;
    }
}

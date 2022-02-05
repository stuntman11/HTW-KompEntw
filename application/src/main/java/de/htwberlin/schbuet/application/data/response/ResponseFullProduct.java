package de.htwberlin.schbuet.application.data.response;

import de.htwberlin.schbuet.application.data.request.RequestTax;
import de.htwberlin.schbuet.application.data.geo.GeoAddress;
import de.htwberlin.schbuet.application.data.main.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFullProduct {

    private UUID id;
    private String name;
    private String description;
    private String category;
    private String itemNumber;
    private RequestTax price;
    private int yearOfProduction;
    private Date createdDate;
    private int quantity;
    private int deliveryTimeInDays;
    private GeoAddress address;

    public ResponseFullProduct(Product product, RequestTax requestTax, GeoAddress geoAddress, ResponseStockItem stockItem) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.itemNumber = product.getItemNumber();
        this.price = requestTax;
        this.yearOfProduction = product.getYearOfProduction();
        this.createdDate = product.getCreatedDate();
        this.address = geoAddress;
        this.quantity = stockItem.getQuantity();
        this.deliveryTimeInDays = stockItem.getDeliveryTimeInDays();
    }
}

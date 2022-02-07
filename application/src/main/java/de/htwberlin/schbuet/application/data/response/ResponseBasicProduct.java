package de.htwberlin.schbuet.application.data.response;

import de.htwberlin.schbuet.application.data.main.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ResponseBasicProduct {

    private UUID id;
    private String name;
    private String description;
    private String category;
    private String itemNumber;
    private int yearOfProduction;
    private Date createdDate;

    public ResponseBasicProduct(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.itemNumber = product.getItemNumber();
        this.yearOfProduction = product.getYearOfProduction();
        this.createdDate = product.getCreatedDate();
    }
}

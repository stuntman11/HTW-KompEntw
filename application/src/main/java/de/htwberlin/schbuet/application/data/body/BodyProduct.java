package de.htwberlin.schbuet.application.data.body;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class BodyProduct {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Category is mandatory")
    private String category;

    @NotBlank(message = "Item number is mandatory")
    private String itemNumber;

    private int priceInCents;

    @Min(1900)
    private int YearOfProduction;
}

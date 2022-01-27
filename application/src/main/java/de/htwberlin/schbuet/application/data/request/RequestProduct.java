package de.htwberlin.schbuet.application.data.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class RequestProduct {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Category is mandatory")
    private String category;

    @NotBlank(message = "Item number is mandatory")
    private String itemNumber;

    @Positive
    private int priceInCents;

    @Min(1900)
    private int YearOfProduction;
}

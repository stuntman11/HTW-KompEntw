package de.htwberlin.schbuet.application.data.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String description;

    private String category;

    private String itemNumber;

    private int priceInCents;

    private int YearOfProduction;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}

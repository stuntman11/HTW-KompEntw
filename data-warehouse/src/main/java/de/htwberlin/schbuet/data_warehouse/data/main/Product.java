package de.htwberlin.schbuet.data_warehouse.data.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uniqueidentifier")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private UUID productId;

    private String name;

    private String description;

    private String category;

    private String itemNumber;

    private int priceInCents;

    private int yearOfProduction;

    @Temporal(TemporalType.TIMESTAMP)
    private Date importDate;
}

package de.htwberlin.schbuet.data_warehouse.data.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class StockItem {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uniqueidentifier")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private UUID productId;

    private int quantity;

    private int deliveryTimeInDays;

    private Double latitude;

    private Double longitude;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastUpdate;
}

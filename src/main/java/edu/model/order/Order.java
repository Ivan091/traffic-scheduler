package edu.model.order;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;


@Table("Metrix")
@Value
@AllArgsConstructor
public class Order {

    @Id
    @With
    Integer id;

    @Embedded.Empty
    Path path;

    Integer seatsNumber;

    LocalDateTime timeRequest;

    public Order(Path path, Integer seatsNumber, LocalDateTime timeRequest) {
        this(null, path, seatsNumber, timeRequest);
    }
}

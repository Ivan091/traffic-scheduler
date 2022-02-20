package edu.model.order;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;


@Table("Metrix")
public record Order(@Id Integer id,
                    @Embedded.Empty Path path,
                    Integer seatsNumber,
                    LocalDateTime timeRequest) {

    public static Order of(Path path, Integer seatsNumber, LocalDateTime timeRequest) {
        return new Order(null, path, seatsNumber, timeRequest);
    }
}

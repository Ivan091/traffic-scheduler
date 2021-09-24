package edu.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity(name = "Metrix")
public class Order {

    @Id
    public Integer id;

    public Integer source;

    public Integer destination;

    public LocalDateTime localDateTime;

    public Order(Integer id, Integer source, Integer destination, LocalDateTime localDateTime) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.localDateTime = localDateTime;
    }

    @Deprecated
    protected Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", source=" + source +
                ", destination=" + destination +
                ", localDateTime=" + localDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss")) +
                '}';
    }
}

package edu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;


public class Order {

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

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("source=" + source)
                .add("destination=" + destination)
                .add("localDateTime=" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .toString();
    }
}

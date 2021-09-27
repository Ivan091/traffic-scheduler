package edu.model;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "Metrix")
public class Order {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public Integer origin;

    public Integer destination;

    public Integer seatsNumber;

    public Timestamp timeRequest;

    public Order(Integer origin, Integer destination, Integer seatsNumber, Timestamp timeRequest) {
        this.origin = origin;
        this.destination = destination;
        this.seatsNumber = seatsNumber;
        this.timeRequest = timeRequest;
    }

    @Deprecated
    protected Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", source=" + origin +
                ", destination=" + destination +
                ", localDateTime=" + timeRequest +
                '}';
    }
}

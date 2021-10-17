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

    @Embedded
    public Path path;

    public Integer seatsNumber;

    public Timestamp timeRequest;

    public Order(Path path, Integer seatsNumber, Timestamp timeRequest) {
        this.path = path;
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
                ", path=" + path +
                ", seatsNumber=" + seatsNumber +
                ", timeRequest=" + timeRequest +
                '}';
    }
}

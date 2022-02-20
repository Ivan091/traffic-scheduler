package edu.service;

import edu.model.order.Order;
import org.springframework.stereotype.Service;


@Service
public final class OrderService {

    public String toCsvHeader() {
        return "Origin,Destination,SeatsNumber,TimeRequest";
    }

    public String toCsv(Order order) {
        return String.format("%s,%s,%s,%s", order.path().origin(), order.path().destination(), order.seatsNumber(), order.timeRequest());
    }
}

package edu.service;

import edu.model.order.Order;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    public String toCsvHeader() {
        return "Origin,Destination,SeatsNumber,TimeRequest";
    }

    public String toCsv(Order order) {
        return String.format("%s,%s,%s,%s", order.getPath().getOrigin(), order.getPath().getDestination(), order.getSeatsNumber(), order.getTimeRequest());
    }
}

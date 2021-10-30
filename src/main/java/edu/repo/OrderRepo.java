package edu.repo;

import edu.model.order.Order;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepo extends CrudRepository<Order, Integer> {

}

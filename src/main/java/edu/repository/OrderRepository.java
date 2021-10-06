package edu.repository;

import edu.repository.entity.Order;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<Order, Integer> {

}

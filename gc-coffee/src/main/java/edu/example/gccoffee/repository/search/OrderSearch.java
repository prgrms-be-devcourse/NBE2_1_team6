package edu.example.gccoffee.repository.search;

import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderStatus;

import java.util.List;

public interface OrderSearch {
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
}

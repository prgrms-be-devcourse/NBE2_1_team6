package edu.example.gccoffee.repository.search;

import edu.example.gccoffee.entity.OrderItem;

import java.util.List;

public interface OrderItemSearch {
    List<OrderItem> findAllByOrderId(Long orderId);
}

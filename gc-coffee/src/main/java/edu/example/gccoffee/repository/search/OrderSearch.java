package edu.example.gccoffee.repository.search;

import edu.example.gccoffee.entity.Order;

import java.util.List;

public interface OrderSearch {
    List<Order> selectAll();
}

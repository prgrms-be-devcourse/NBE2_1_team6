package edu.example.gccoffee.repository;


import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

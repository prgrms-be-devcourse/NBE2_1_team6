package edu.example.gccoffee.repository;


import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.Product;
import edu.example.gccoffee.repository.search.OrderSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderSearch {
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItem WHERE o.email = :email")
    List<Order> findByEmail(@Param("email") String email);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItem")
    List<Order> findAll();
}

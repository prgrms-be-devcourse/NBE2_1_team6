package edu.example.gccoffee.repository;


import edu.example.gccoffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

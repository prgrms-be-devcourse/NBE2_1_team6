package edu.example.gccoffee.repository;


import edu.example.gccoffee.entity.Product;
import edu.example.gccoffee.repository.search.ProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {
}

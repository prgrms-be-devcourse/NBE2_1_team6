package edu.example.gccoffee.repository;


import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.entity.Product;
import edu.example.gccoffee.repository.search.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    @Query("select p from Product p")
    Page<ProductDTO> getPage(Pageable pageable);
}

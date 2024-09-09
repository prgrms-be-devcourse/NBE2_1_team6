package edu.example.gccoffee.repository.search;

import edu.example.gccoffee.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearch {
    Page<ProductDTO> searchDTO(Pageable pageable);
}

package edu.example.gccoffee.repository.search;

import com.querydsl.jpa.impl.JPAQueryFactory;
import edu.example.gccoffee.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ProductSearchImpl implements ProductSearch {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ProductDTO> searchDTO(Pageable pageable) {
        return null;
    }
}

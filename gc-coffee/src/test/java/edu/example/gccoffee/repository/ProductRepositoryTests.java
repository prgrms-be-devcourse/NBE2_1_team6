package edu.example.gccoffee.repository;

import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {
        //GIVEN
        Product product = Product.builder().productName("상품1").category(Category.COFFEE_BEAN_PACKAGE.name()).price(3000).description("상품 추가 테스트").build();

        //WHEN
        Product savedProduct = productRepository.save(product);

        //THEN
        assertNotNull(savedProduct);
        assertEquals(1, savedProduct.getProductId());
        assertEquals("상품1", savedProduct.getProductName());
        assertEquals("COFFEE_BEAN_PACKAGE", savedProduct.getCategory());
        assertEquals(3000, savedProduct.getPrice());
        assertEquals("상품 추가 테스트", savedProduct.getDescription());
    }

    @Test
    public void testDelete() {
        //GIVEN
        Long productId = 1L;

        //WHEN
        assertTrue(productRepository.existsById(productId));
        productRepository.deleteById(productId);

        //THEN
        assertFalse(productRepository.existsById(productId));
    }
}

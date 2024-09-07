package edu.example.gccoffee.repository;

import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertEquals(4, savedProduct.getProductId());
        assertEquals("상품1", savedProduct.getProductName());
        assertEquals("COFFEE_BEAN_PACKAGE", savedProduct.getCategory());
        assertEquals(3000, savedProduct.getPrice());
        assertEquals("상품 추가 테스트", savedProduct.getDescription());
    }

    @Test
    @DisplayName("업데이트 테스트")
    public void testUpdate() {
        Long productId = 2L;

        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent(), "Product should be present");

        Product product = foundProduct.get();
        product.changeProductName("제품명 재업데이트");
        product.changePrice(100000);
        product.changeDescription("제품 설명 재업데이트");

        productRepository.save(product);

        assertThat(product.getProductName()).isEqualTo("제품명 재업데이트");
        assertThat(product.getPrice()).isEqualTo(100000);
        assertThat(product.getDescription()).isEqualTo("제품 설명 재업데이트");
    }

    @Test
    @DisplayName("읽어오기 테스트")
    public void testRead() {
        Long productId = 2L;

        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent(), "Product should be present");

        Product product = foundProduct.get();
        assertNotNull(product);
        assertEquals(2, product.getProductId());
        assertEquals(100000, product.getPrice());
        assertEquals("제품명 재업데이트", product.getProductName());
        assertEquals("제품 설명 재업데이트", product.getDescription());
    }

    @Test
    public void testDelete() {
        //GIVEN
        Long productId = 2L;

        //WHEN
        assertTrue(productRepository.existsById(productId));
        productRepository.deleteById(productId);

        //THEN
        assertFalse(productRepository.existsById(productId));
    }
}

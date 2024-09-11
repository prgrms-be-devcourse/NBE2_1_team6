package edu.example.gccoffee.repository;

import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;
//
//    @Test
//    @org.junit.jupiter.api.Order(1)
//    public void testInsert() {
//        //GIVEN
//        Product product = Product.builder().productName("상품1").category(Category.COFFEE_BEAN_PACKAGE).price(3000).description("상품 추가 테스트").build();
//
//        //WHEN
//        Product savedProduct = productRepository.save(product);
//
//        //THEN
//        assertNotNull(savedProduct);
//        assertEquals(1, savedProduct.getProductId());
//        assertEquals("상품1", savedProduct.getProductName());
//        assertEquals(Category.COFFEE_BEAN_PACKAGE, savedProduct.getCategory());
//        assertEquals(3000, savedProduct.getPrice());
//        assertEquals("상품 추가 테스트", savedProduct.getDescription());
//
//        log.info(savedProduct);
//    }

    @Test
//    @org.junit.jupiter.api.Order(2)
    public void testInsertTen() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            //GIVEN
            Product product = Product.builder().productName("상품" + i).category(Category.COFFEE_BEAN_PACKAGE).price(new Random().nextInt(1, 5) * 1000).description("상품" + i + " 추가").build();

            //WHEN
            Product savedProduct = productRepository.save(product);

            //THEN
            assertNotNull(savedProduct);

            log.info(savedProduct);
        });
//        assertEquals(11, productRepository.count());
    }

    @Test
    @Transactional
    @Commit
    @org.junit.jupiter.api.Order(3)
    @DisplayName("업데이트 테스트")
    public void testUpdate() {
        Long productId = 2L;

        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent(), "Product should be present");

        Product product = foundProduct.get();
        product.changeProductName("제품명 업데이트");
        product.changePrice(100000);
        product.changeDescription("제품 설명 업데이트");

        assertEquals("제품명 업데이트", product.getProductName(), "제품명 일치하지 않음");
        assertEquals(100000, product.getPrice(), "가격 일치하지 않음");
        assertEquals("제품 설명 업데이트", product.getDescription(), "제품 설명 일치하지 않음");
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void testDelete() {
        //GIVEN
        Long productId = 1L;

        //WHEN
        assertTrue(productRepository.existsById(productId));
        productRepository.deleteById(productId);

        //THEN
        assertFalse(productRepository.existsById(productId));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("읽어오기 테스트")
    public void testRead() {
        Long productId = 2L;

        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent(), "Product should be present");

        Product product = foundProduct.get();
        assertNotNull(product);
        assertEquals(2, product.getProductId());
        assertEquals(100000, product.getPrice());
        assertEquals("제품명 업데이트", product.getProductName());
        assertEquals("제품 설명 업데이트", product.getDescription());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    public void testReadAll() {
        //GIVEN
        Pageable pageable = PageRequest.of(0, 5, Sort.by("productId").descending());

        //WHEN
        Page<Product> productPage = productRepository.findAll(pageable);

        //THEN
        assertNotNull(productPage);
        assertEquals(10, productPage.getTotalElements());
        assertEquals(2, productPage.getTotalPages());
        assertEquals(0, productPage.getNumber());
        assertEquals(5, productPage.getSize());
        assertEquals(5, productPage.getContent().size());

        productPage.forEach(System.out::println);
    }
}

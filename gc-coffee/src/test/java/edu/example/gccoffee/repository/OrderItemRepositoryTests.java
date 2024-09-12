package edu.example.gccoffee.repository;

import edu.example.gccoffee.config.QueryDslConfig;
import edu.example.gccoffee.entity.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Log4j2
@Import(QueryDslConfig.class)
public class OrderItemRepositoryTests {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    void setupBeforeAllMethod() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Order order = Order.builder()
                    .email("user" + i + "@gmail.com")
                    .address("Busan")
                    .postCode(25342)
                    .orderStatus( i > 5 ? OrderStatus.DELIVERING : OrderStatus.NOT_DELIVERY)
                    .build();

            Product product = Product.builder()
                    .productName("상품" + i)
                    .category(Category.COFFEE_BEAN_PACKAGE)
                    .price(new Random().nextInt(1, 5) * 1000)
                    .description("상품" + i + " 추가")
                    .build();

            order = orderRepository.save(order);
            product = productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .price(10000)
                    .quantity(10)
                    .category(Category.COFFEE_BEAN_PACKAGE)
                    .order(order)
                    .product(product)
                    .build();

            orderItemRepository.save(orderItem);
        });
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("주문목록 삽입 테스트")
    void should_DataSaved_When_Save() {
        Order order = Order.builder().orderId(1L).build();
        Product product = Product.builder().productId(1L).build();

        OrderItem orderItem = OrderItem.builder()
                .price(10000)
                .quantity(15)
                .category(Category.COFFEE_BEAN_PACKAGE)
                .order(order)
                .product(product)
                .build();

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        assertEquals(orderItem, savedOrderItem);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("주문목록 단일조회 테스트 (성공)")
    void should_IsPresent_When_IdIs1L() {
        Optional<OrderItem> foundOrderItem = orderItemRepository.findById(1L);

        assertTrue(foundOrderItem.isPresent());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("주문목록 단일조회 테스트 (실패)")
    void should_IsEmpty_When_IdIs999L() {
        Optional<OrderItem> foundOrderItem = orderItemRepository.findById(999L);

        assertTrue(foundOrderItem.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("주문목록 전체조회 테스트")
    void should_Count10_When_findAll() {
        List<OrderItem> foundOrderItems = orderItemRepository.findAll();

        assertEquals(10, foundOrderItems.size());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("주문목록 수정 테스트")
    void should_DataUpdated_When_QuantityChanged() {
        Optional<OrderItem> foundOrderItem = orderItemRepository.findById(1L);

        OrderItem orderItem = foundOrderItem.get();
        orderItem.changeQuantity(5);
        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);

        assertEquals(orderItem, updatedOrderItem);
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("주문목록 삭제 테스트")
    void should_DataDeleted_When_DeleteById() {
        orderItemRepository.deleteById(1L);

        assertEquals(10, orderRepository.findAll().size());
    }
}

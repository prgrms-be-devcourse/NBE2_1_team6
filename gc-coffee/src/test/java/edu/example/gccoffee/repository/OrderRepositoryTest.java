package edu.example.gccoffee.repository;

import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderStatus;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Order order = Order.builder()
                                .email("user" + i + "@gmail.com")
                                .address("Busan")
                                .postCode(25342)
                                .orderStatus( i > 3 ? OrderStatus.DELIVERING : OrderStatus.NOT_DELIVERY)
                                .build();

            //WHEN - 엔티티 저장
            Order savedOrder = orderRepository.save(order);

            //THEN
            assertNotNull(savedOrder);
            assertEquals(i, savedOrder.getOrderId());
        });
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testRead() {
        Long orderId = 1L;

        Optional<Order> order = orderRepository.findById(orderId);

        log.info(order.isPresent());
        assertNotNull(order);
        assertEquals(orderId, order.get().getOrderId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testUpdate() {
        Long orderId = 1L;
        String address = "변경 주소";
        String email = "변경@gmail.com";
        int postCode = 11111;


        Optional<Order> order = orderRepository.findById(orderId);
        assertTrue(order.isPresent());

        Order orderToUpdate = order.get();
        orderToUpdate.changeEmail(email);
        orderToUpdate.changeAddress(address);
        orderToUpdate.changePostCode(postCode);

        orderRepository.save(orderToUpdate);

        order = orderRepository.findById(orderId);
        assertTrue(order.isPresent());
        assertEquals(orderId, order.get().getOrderId());
        assertEquals(email, order.get().getEmail());
        assertEquals(address, order.get().getAddress());
        assertEquals(postCode, order.get().getPostCode());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void testDelete() {
        Long orderId = 5L;

        assertTrue(orderRepository.existsById(orderId));

        orderRepository.deleteById(orderId);
        assertFalse(orderRepository.existsById(orderId));
    }
}

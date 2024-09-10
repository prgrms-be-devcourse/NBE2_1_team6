package edu.example.gccoffee.service;


import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.entity.Product;
import edu.example.gccoffee.exception.OrderException;
import edu.example.gccoffee.exception.ProductException;
import edu.example.gccoffee.repository.OrderItemRepository;
import edu.example.gccoffee.repository.OrderRepository;
import edu.example.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    public OrderDTO add(OrderDTO orderDTO){   //등록
        orderRepository.save(orderDTO.toEntity());
        List<OrderItem> orderItems = orderDTO.getOrderItem();
        for (OrderItem orderItem : orderItems) {
            OrderItemDTO orderItemDTO = new OrderItemDTO(orderItem);
            Order order = orderRepository.findById(orderDTO.getOrderId()).get();
            Product product = productRepository.findById(orderItemDTO.getProductId()).get();

            orderItem.setOrder(order);
            orderItem.setProduct(product);

            // OrderItem 저장
            orderItemRepository.save(orderItem);
        }
        return orderDTO;
    }
//반환 타입 수정
    public OrderDTO read(String email) {     //조회
        Optional<Order> order = orderRepository.findByEmail(email);
        if (order.isPresent()) {
            return new OrderDTO(order.get());
        } else {
            throw OrderException.ORDER_NOT_FOUND.getOrderTaskException();
        }
    }

    public OrderDTO update(OrderDTO orderDTO){
        Optional<Order> foundOrder = orderRepository.findById(orderDTO.getOrderId());   //수정하려는 상품을 데이터베이스에서 조회해서
        Order order = foundOrder.orElseThrow(ProductException.PRODUCT_NOT_FOUND::get);

        try {
            order.changeEmail(order.getEmail());
            order.changeAddress(order.getAddress());
            order.changePostCode(order.getPostCode());

            return new OrderDTO(order); //변경된 상품을 반환
        } catch(Exception e) {
            log.error("--- " + e.getMessage());
            throw OrderException.ORDER_NOT_FOUND.getOrderTaskException();
        }
    }

    public void delete(Long orderId){
        orderRepository.deleteById(orderId);
    }
}

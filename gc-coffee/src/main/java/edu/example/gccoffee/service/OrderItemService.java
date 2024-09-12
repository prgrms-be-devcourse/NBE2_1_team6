package edu.example.gccoffee.service;


import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.entity.Product;
import edu.example.gccoffee.exception.OrderException;
import edu.example.gccoffee.repository.OrderItemRepository;
import edu.example.gccoffee.repository.OrderRepository;
import edu.example.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public List<OrderItem> modify(List<OrderItemDTO> orderItemDTOs, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        // 기존의 주문 아이템을 업데이트
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            // 주문 아이템 ID로 기존 아이템을 찾습니다.
            OrderItem orderItem = orderItemRepository.findById(orderItemDTO.getOrderItemId())
                    .orElseThrow(() -> new RuntimeException("OrderItem not found"));

            // 아이템 정보를 업데이트합니다.
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPrice(orderItemDTO.getPrice());
            orderItem.setCategory(Category.valueOf(orderItemDTO.getCategory()));

            // 변경된 아이템을 저장합니다.
            orderItemRepository.save(orderItem);
        }

        // 업데이트된 아이템 목록을 반환합니다.
        return orderItemRepository.findAllByOrderId(orderId);
    }

    public List<OrderItemDTO> readAll(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        if (orderItems.isEmpty()) {
            throw OrderException.ORDERITEM_NOT_FOUND.getOrderTaskException();
        }

        orderItems.forEach(item -> orderItemDTOs.add(new OrderItemDTO(item)));
        return orderItemDTOs;
    }

    public OrderItem toEntity(OrderItemDTO dto) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));
        Category category = Category.valueOf(dto.getCategory());

        return OrderItem.builder()
                .orderItemId(dto.getOrderItemId())
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .category(category)
                .order(order)
                .product(product)
                .build();
    }
}

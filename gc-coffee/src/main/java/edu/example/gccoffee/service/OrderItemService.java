package edu.example.gccoffee.service;


import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.entity.Product;
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

    public List<OrderItem> modify(List<OrderItemDTO> orderItemDTOs,Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        List<OrderItem> originOrder = order.getOrderItem();
        for (OrderItem orderItem : originOrder) {
            orderItemRepository.delete(orderItem);
        }
        List<OrderItem> itemList = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            orderItemDTO.setOrderId(orderId);
            orderItemRepository.save(toEntity(orderItemDTO));
            itemList.add(toEntity(orderItemDTO));
        }
        return itemList;
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

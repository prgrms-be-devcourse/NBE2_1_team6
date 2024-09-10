package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    private Long orderItemId;
    private int quantity;
    private int price;
    private String category;
    private Long orderId;
    private Long productId;

    public OrderItemDTO(OrderItem orderItem) {
        this.orderItemId = orderItem.getOrderItemId();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.category = orderItem.getCategory().name();
        this.orderId = orderItem.getOrder().getOrderId();
        this.productId = orderItem.getProduct().getProductId();
    }
}


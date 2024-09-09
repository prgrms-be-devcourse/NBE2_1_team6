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
    private Category category;
    private Order order;
    private Product product;

    public OrderItemDTO(OrderItem orderItem) {
        this.orderItemId = orderItem.getOrderItemId();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.category = orderItem.getCategory();
        this.order = orderItem.getOrderId();
        this.product = orderItem.getProductId();
    }

    public OrderItem toEntity(){
        return OrderItem.builder()
                .orderItemId(orderItemId)
                .quantity(quantity)
                .price(price)
                .category(category)
                .orderId(order)
                .productId(product)
                .build();
    }
}

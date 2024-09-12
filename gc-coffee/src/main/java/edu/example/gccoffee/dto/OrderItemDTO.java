package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "주문 상품 목록 정보(OrderItemDTO)", description = "고객의 주문에서 상품 목록을 담고 있는 객체")
public class OrderItemDTO {
    @Schema(description = "주문 상품 번호", example = "1")
    private Long orderItemId;
    @Schema(description = "상품 수량", example = "2")
    private int quantity;
    @Schema(description = "상품 총 가격", example = "12000")
    private int price;
    @Schema(description = "카테고리", example = "COFFEE_BEAN_PACKAGE")
    private String category;
    @Schema(description = "주문 번호", example = "1")
    private Long orderId;
    @Schema(description = "상품 번호", example = "1")
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


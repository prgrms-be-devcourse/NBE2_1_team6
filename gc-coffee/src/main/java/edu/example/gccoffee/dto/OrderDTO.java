package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.entity.OrderStatus;
import edu.example.gccoffee.entity.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String email;
    private String address;
    private int postCode;
    private List<OrderItemDTO> orderItem;

    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.NOT_DELIVERY;

    public OrderDTO(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postCode = order.getPostCode();
        this.orderStatus = order.getOrderStatus();
        // orderItem 리스트 초기화
        this.orderItem = new ArrayList<>();

        // order.getOrderItem()이 null이 아닐 때만 처리
        if (order.getOrderItem() != null) {
            for (OrderItem orderItem : order.getOrderItem()) {
                this.orderItem.add(new OrderItemDTO(orderItem));
            }
        }
    }

    public Order toEntity(){
        return Order.builder()
                .orderId(orderId)
                .email(email)
                .address(address)
                .postCode(postCode)
                .orderStatus(orderStatus)
                .build();

    }
}

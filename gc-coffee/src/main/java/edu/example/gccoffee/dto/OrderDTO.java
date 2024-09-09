package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<OrderItem> orderItem;
    private OrderStatus orderStatus;

    public OrderDTO(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.address =order.getAddress();
        this.postCode = order.getPostCode();
        this.orderItem = order.getOrderItem();
        this.orderStatus = order.getOrderStatus();
    }

    public Order toEntity(){
        return Order.builder()
                .orderId(orderId)
                .email(email)
                .address(address)
                .postCode(postCode)
                .orderItem(orderItem)
                .orderStatus(orderStatus)
                .build();

    }
}

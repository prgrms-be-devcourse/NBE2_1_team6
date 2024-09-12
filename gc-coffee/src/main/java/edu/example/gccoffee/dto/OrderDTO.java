package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.entity.OrderStatus;
import edu.example.gccoffee.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "주문 정보(OrderDTO)", description = "고객의 주문에 관한 정보를 담고 있는 객체")
public class OrderDTO {
    @Schema(description = "주문 번호", example = "1")
    private Long orderId;

    @NotEmpty
    @Schema(description = "이메일", example = "hong@gmail.com")
    private String email;

    @NotEmpty
    @Schema(description = "주소", example = "서울시 강남구")
    private String address;

    @NotNull(message = "Post code must not be null")
    @Schema(description = "우편번호", example = "12351")
    private int postCode;

    @NotEmpty
    @Schema(description = "주문상품 목록 정보")
    private List<OrderItemDTO> orderItem;

    @Builder.Default
    @Schema(description = "배송 상태", example = "NOT_DELIVERY")
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

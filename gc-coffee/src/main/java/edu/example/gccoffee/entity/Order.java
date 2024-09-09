package edu.example.gccoffee.entity;

import edu.example.gccoffee.dto.OrderItemDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String email;

    private String address;

    private int postCode;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItem;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime createAt;

    public void addOrderItems(List<OrderItemDTO> orderItemsDTO){
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO dto : orderItemsDTO) {
//            Product product = productService.findById(dto.getProductId()); // 제품 정보 가져오기

            OrderItem orderItem = OrderItem.builder()
//                    .product(product)
                    .quantity(dto.getQuantity())
                    .price(dto.getPrice())
                    .order(dto.getOrder())
                    .category(dto.getCategory())
                    .build();

            orderItems.add(orderItem);
        }
    }


    public void changeAddress(String address) {
        this.address = address;
    }

    public void changePostCode(int postCode) {
        this.postCode = postCode;
    }

    public void changeEmail(String email) {
        this.email = email;
    }
}

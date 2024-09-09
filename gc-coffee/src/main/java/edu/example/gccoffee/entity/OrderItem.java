package edu.example.gccoffee.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "order_items")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product productId;

    private int quantity;

    private int price;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}

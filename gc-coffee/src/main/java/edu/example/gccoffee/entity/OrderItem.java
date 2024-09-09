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
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private int price;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

}

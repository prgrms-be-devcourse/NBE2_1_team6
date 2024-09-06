package edu.example.gccoffee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name= "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String email;

    private String address;

    private int postCode;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItem;

    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime createAt;

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

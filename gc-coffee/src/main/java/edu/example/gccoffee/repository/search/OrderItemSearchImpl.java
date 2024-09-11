package edu.example.gccoffee.repository.search;

import com.querydsl.jpa.impl.JPAQueryFactory;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.entity.QOrderItem;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderItemSearchImpl implements OrderItemSearch {
    private final JPAQueryFactory jpaQueryFactory;
    private final QOrderItem qOrderItem = QOrderItem.orderItem;

    @Override
    public List<OrderItem> findAllByOrderId(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderId cannot be null.");
        }

        return jpaQueryFactory
                .selectFrom(qOrderItem)
                .where(qOrderItem.order.orderId.eq(orderId))
                .fetch();
    }
}

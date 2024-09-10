package edu.example.gccoffee.repository.search;

import com.querydsl.jpa.impl.JPAQueryFactory;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderStatus;
import edu.example.gccoffee.entity.QOrder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderSearchImpl implements OrderSearch {
    private final JPAQueryFactory jpaQueryFactory;
    private final QOrder qOrder = QOrder.order;

    @Override
    public List<Order> findAllByOrderStatus(OrderStatus orderStatus) {
        if (orderStatus == null) {
            throw new IllegalArgumentException("OrderStatus cannot be null.");
        }

        return jpaQueryFactory
                .selectFrom(qOrder)
                .where(qOrder.orderStatus.eq(orderStatus))
                .fetch();
    }
}

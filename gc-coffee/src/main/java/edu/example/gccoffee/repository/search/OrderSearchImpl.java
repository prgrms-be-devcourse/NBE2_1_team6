package edu.example.gccoffee.repository.search;

import com.querydsl.jpa.impl.JPAQueryFactory;
import edu.example.gccoffee.entity.Order;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderSearchImpl implements OrderSearch {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Order> selectAll() {
        return null;
    }
}

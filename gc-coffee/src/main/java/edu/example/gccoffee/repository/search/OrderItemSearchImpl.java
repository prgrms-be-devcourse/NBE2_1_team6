package edu.example.gccoffee.repository.search;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderItemSearchImpl implements OrderItemsSearch {
    private final JPAQueryFactory jpaQueryFactory;
}

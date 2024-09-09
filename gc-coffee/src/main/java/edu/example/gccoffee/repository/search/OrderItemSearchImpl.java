package edu.example.gccoffee.repository.search;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderItemSearchImpl implements OrderItemSearch {
    private final JPAQueryFactory jpaQueryFactory;
}

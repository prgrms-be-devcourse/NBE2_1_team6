package edu.example.gccoffee.repository.search;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductSearchImpl implements ProductSearch {
    private final JPAQueryFactory jpaQueryFactory;
}

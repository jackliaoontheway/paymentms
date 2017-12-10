package com.noone.paymentms.domain;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> , QueryDslPredicateExecutor<OrderItem> {




}
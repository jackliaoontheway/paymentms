package com.noone.paymentms.domain;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface NooneOrderRepository extends CrudRepository<NooneOrder, Long> , QueryDslPredicateExecutor<NooneOrder> {




}
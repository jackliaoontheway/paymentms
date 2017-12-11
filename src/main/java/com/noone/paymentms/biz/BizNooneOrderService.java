package com.noone.paymentms.biz;

import java.util.List;

import com.noone.paymentms.common.basemodel.BizResponse;
import com.noone.paymentms.domain.OrderItem;

public interface BizNooneOrderService {
	
	BizResponse<String> createOrder(List<OrderItem> items, Double totalFee);
	
}
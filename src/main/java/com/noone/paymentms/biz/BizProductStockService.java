package com.noone.paymentms.biz;

import java.util.List;

import com.noone.paymentms.common.basemodel.BizResponse;
import com.noone.paymentms.domain.OrderItem;

public interface BizProductStockService {
	
	BizResponse<List<OrderItem>> retriveProductStockByReadRfid();
	
}
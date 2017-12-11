package com.noone.paymentms.biz;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noone.paymentms.common.basemodel.BizResponse;
import com.noone.paymentms.domain.NooneOrder;
import com.noone.paymentms.domain.NooneOrderRepository;
import com.noone.paymentms.domain.OrderItem;
import com.noone.paymentms.domain.OrderItemRepository;

@Component
public class BizNooneOrderServiceImpl implements BizNooneOrderService {

	@Autowired
	private NooneOrderRepository nooneOrderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	@Transactional
	public BizResponse<String> createOrder(List<OrderItem> items, Double totalFee) {
		BizResponse<String> bizResp = new BizResponse<String>();
		NooneOrder order = new NooneOrder();
		order.setStatus("PENDING");
		order.setOrderNum("NO." + new Date().getTime());
		order.setTotalFee(totalFee);
		NooneOrder savedOrder = nooneOrderRepository.save(order);

		for (OrderItem item : items) {
			item.setOrderId(savedOrder.getId());
			orderItemRepository.save(item);
		}
		bizResp.setData(savedOrder.getOrderNum());
		return bizResp;
	}

}

package com.noone.paymentms.biz;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noone.paymentms.biz.pay.util.PayResultStatus;
import com.noone.paymentms.biz.pay.util.PayUtil;
import com.noone.paymentms.common.basemodel.BizResponse;
import com.noone.paymentms.domain.NooneOrder;
import com.noone.paymentms.domain.NooneOrderRepository;
import com.noone.paymentms.domain.OrderItem;
import com.noone.paymentms.domain.OrderItemRepository;
import com.noone.paymentms.domain.ProductStock;
import com.noone.paymentms.domain.ProductStockRepository;
import com.noone.paymentms.domain.QOrderItem;
import com.noone.paymentms.domain.QProductStock;
import com.querydsl.core.types.dsl.BooleanExpression;

@Component
public class BizNooneOrderServiceImpl implements BizNooneOrderService {

	@Autowired
	private NooneOrderRepository nooneOrderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	ProductStockRepository productStockRepository;

	@Override
	@Transactional
	public BizResponse<NooneOrder> createOrder(List<OrderItem> items, Double totalFee) {
		BizResponse<NooneOrder> bizResp = new BizResponse<NooneOrder>();
		NooneOrder order = new NooneOrder();
		order.setStatus("PENDING");
		order.setOrderNum("NO." + new Date().getTime());
		order.setTotalFee(totalFee);
		order.setBaseField(-99999L);
		NooneOrder savedOrder = nooneOrderRepository.save(order);

		for (OrderItem item : items) {
			item.setOrderId(savedOrder.getId());
			orderItemRepository.save(item);
		}
		bizResp.setData(order);
		return bizResp;
	}

	@Override
	@Transactional
	public BizResponse<NooneOrder> pay(Long id, String payCode) {

		BizResponse<NooneOrder> bizResp = new BizResponse<NooneOrder>();

		NooneOrder dbOrder = nooneOrderRepository.findOne(id);

		if (dbOrder != null && dbOrder.getTotalFee() != null) {
			Double totalFee = dbOrder.getTotalFee()*1000;
			PayResultStatus payResultStatus = PayUtil.getInstance().pay(payCode, dbOrder.getOrderNum(),
					 (totalFee.intValue()) + "");

			if (payResultStatus != null) {
				dbOrder.setStatus(payResultStatus.name());
				nooneOrderRepository.save(dbOrder);
				if (payResultStatus.equals(PayResultStatus.SUCCESS)) {
					updateProductStock(id);
				}
			} else {
				dbOrder.setStatus(PayResultStatus.EXCEPTION.name());
			}
		} else {
			dbOrder.setStatus(PayResultStatus.EXCEPTION.name());
		}
		
		bizResp.setData(dbOrder);
		return bizResp;
	}

	private void updateProductStock(Long id) {
		BooleanExpression predicate = QOrderItem.orderItem.orderId.eq(id);
		Iterable<OrderItem> iterable = orderItemRepository.findAll(predicate);
		Iterator<OrderItem> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			OrderItem orderItem = iterator.next();
			String rfids = orderItem.getRfids();
			if (rfids != null) {
				String[] rfidArry = rfids.split("&");
				if (rfidArry.length > 0) {
					for (String rfid : rfidArry) {
						ProductStock productStock = productStockRepository
								.findOne(QProductStock.productStock.rfid.eq(rfid));
						if (productStock != null) {
							productStock.setStatus("PAID");
							productStockRepository.save(productStock);
						}
					}
				}
			}
		}
	}

	@Override
	public BizResponse<NooneOrder> queryOrderStatus(Long id) {

		BizResponse<NooneOrder> bizResp = new BizResponse<NooneOrder>();


		NooneOrder dbOrder = nooneOrderRepository.findOne(id);
		
		PayResultStatus payResultStatus = PayUtil.getInstance().queryPayStatus(dbOrder.getOrderNum());

		if (payResultStatus != null) {
			dbOrder.setStatus(payResultStatus.name());
			nooneOrderRepository.save(dbOrder);
			if (payResultStatus.equals(PayResultStatus.SUCCESS)) {
				updateProductStock(id);
			}
		} else {
			dbOrder.setStatus(PayResultStatus.EXCEPTION.name());
		}
		
		bizResp.setData(dbOrder);
		return bizResp;
	}

//	@Override
//	public BizResponse<NooneOrder> completeOrder(Long id) {
//
//		BizResponse<NooneOrder> bizResp = new BizResponse<NooneOrder>();
//
//		NooneOrder dbOrder = nooneOrderRepository.findOne(id);
//
//		if (dbOrder != null) {
////			 PayUtil.getInstance().pay(payCode, dbOrder.getOrderNum(),
////			 (dbOrder.getTotalFee() * 1000) + "");
//			PayResultStatus payResultStatus = PayUtil.getInstance().pay(payCode, dbOrder.getOrderNum(),
//					(dbOrder.getTotalFee()) + "");
//
//			if (payResultStatus != null) {
//				dbOrder.setStatus(payResultStatus.name());
//				nooneOrderRepository.save(dbOrder);
//				if (payResultStatus.equals(PayResultStatus.SUCCESS)) {
//					updateProductStock(id);
//				}
//			} else {
//				dbOrder.setStatus(PayResultStatus.EXCEPTION.name());
//			}
//		}
//		
//		bizResp.setData(dbOrder);
//		return bizResp;
//	}

}

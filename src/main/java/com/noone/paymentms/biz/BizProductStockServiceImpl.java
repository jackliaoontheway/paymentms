package com.noone.paymentms.biz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.noone.paymentms.common.basemodel.BizResponse;
import com.noone.paymentms.domain.OrderItem;
import com.noone.paymentms.domain.ProductStock;
import com.noone.paymentms.domain.ProductStockRepository;
import com.noone.paymentms.domain.QProductStock;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.rfid.reader.RFIDfactory;

@Component
public class BizProductStockServiceImpl implements BizProductStockService {

	@Autowired
	ProductStockRepository productStockRepository;

	@Override
	public BizResponse<List<OrderItem>> retriveProductStockByReadRfid() {

		BizResponse<List<OrderItem>> bizResp = new BizResponse<List<OrderItem>>();

		RFIDfactory factory = RFIDfactory.getInstance();
		List<String> list = factory.readAllRFID("COM3");

		System.out.println("rfid :" + list);

		if (list == null || list.size() == 0) {
			bizResp.addError("感应失败,请检查机器.");
			return bizResp;
		}

		List<ProductStock> stockList = new ArrayList<ProductStock>();

		for (int i = 0; i < list.size(); i++) {
			String rfid = list.get(i);
			ProductStock stock = getProductStockByCriteria(rfid);
			if (stock != null) {
				stockList.add(stock);
			} else {

			}
		}

		if (stockList.size() == 0) {
			bizResp.addError("库存异常.");
			return bizResp;
		}

		if (list.size() != stockList.size()) {
			bizResp.addError("读取失败,请检查机器.");
			return bizResp;
		}

		convertToOrderItem(stockList, bizResp);
		return bizResp;
	}

	private List<OrderItem> convertToOrderItem(List<ProductStock> stockList, BizResponse<List<OrderItem>> bizResp) {
		List<OrderItem> list = new ArrayList<OrderItem>();
		Map<String, OrderItem> map = new HashMap<String, OrderItem>();

		Double totalFee = 0.0;
		for (ProductStock stock : stockList) {
			// 已经支付的 不要显示
			if ("PAID".equals(stock.getStatus())) {
				continue;
			}
			String sku = stock.getSku();
			OrderItem item = map.get(sku);

			Double price = Double.parseDouble(StringUtils.isEmpty(stock.getPrice()) ? "0" : stock.getPrice());
			String rfid = stock.getRfid();
			if (item == null) {
				item = new OrderItem();
				item.setSku(sku);
				item.setName(stock.getName());
				item.setPrice(price);
				item.setRfids(rfid);
				item.setQty(1);
				item.setItemFee(price);
				map.put(sku, item);
			} else {
				item.setQty(item.getQty() + 1);
				item.setRfids(item.getRfids() + "&" + rfid);
				item.setItemFee(item.getItemFee() + price);
			}
			
			double convertedItemFee = new BigDecimal(item.getItemFee()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			item.setItemFee(convertedItemFee);
			
			totalFee += price;
		}

		list.addAll(map.values());
		double convertedTotalFee = new BigDecimal(totalFee).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		bizResp.setTotalFee(convertedTotalFee);
		bizResp.setData(list);

		return list;
	}

	public ProductStock getProductStockByCriteria(String rfid) {
		ProductStock stock = null;
		try {
			BooleanExpression predicate = QProductStock.productStock.rfid.eq(rfid);
			stock = productStockRepository.findOne(predicate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stock;
	}

}

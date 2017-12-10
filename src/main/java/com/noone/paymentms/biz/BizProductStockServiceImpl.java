package com.noone.paymentms.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noone.paymentms.common.basemodel.BizResponse;
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
	public BizResponse<List<ProductStock>> retriveProductStockByReadRfid() {

		BizResponse<List<ProductStock>> bizResp = new BizResponse<List<ProductStock>>();

		RFIDfactory factory = RFIDfactory.getInstance();
		List<String> list = factory.readAllRFID("COM4");

		if (list == null) {
			bizResp.addError("感应失败,请检查机器.");
			return bizResp;
		}

		List<ProductStock> stockResult = new ArrayList<ProductStock>();

		for (int i = 0; i < list.size(); i++) {
			String rfid = list.get(i);
			ProductStock stock = getProductStockByCriteria(rfid);
			if (getProductStockByCriteria(rfid) != null) {
				stockResult.add(stock);
			} else {

			}
		}
		bizResp.setData(stockResult);
		return bizResp;
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

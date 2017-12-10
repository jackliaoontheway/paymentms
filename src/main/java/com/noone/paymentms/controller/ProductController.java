package com.noone.paymentms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.noone.paymentms.biz.BizProductStockService;
import com.noone.paymentms.common.basemodel.BizResponse;
import com.noone.paymentms.common.basemodel.ServerResponse;
import com.noone.paymentms.domain.ProductStock;

@RestController
@RequestMapping("/paymentms/productstock")
public class ProductController extends BaseController {

	@Autowired
	BizProductStockService bizProductStockService;

	@GetMapping("/readrfid")
	public @ResponseBody ServerResponse<List<ProductStock>> readRFID() {

		ServerResponse<List<ProductStock>> serverResponse = new ServerResponse<List<ProductStock>>();

		BizResponse<List<ProductStock>> bizResp = bizProductStockService.retriveProductStockByReadRfid();
		serverResponse.setData(bizResp.getData());

		return serverResponse;
	}

}
package com.noone.paymentms.controller.model;

import java.util.List;

import com.noone.paymentms.domain.OrderItem;

import lombok.Data;

@Data
public class ViewNooneOrder {

	private Double totalFee;
	private List<OrderItem> orderItemList;

}

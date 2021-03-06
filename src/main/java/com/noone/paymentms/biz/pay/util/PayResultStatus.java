package com.noone.paymentms.biz.pay.util;

public enum PayResultStatus {
	SUCCESS, // —支付成功

	REFUND, // —转入退款

	NOTPAY, // —未支付

	CLOSED, // —已关闭

	REVOKED, // —已撤销

	USERPAYING, // —用户支付中
	
	EXCEPTION,

	PAYERROR; // —支付失败(其他原因，如银行返回失败)

	public static PayResultStatus getByCode(String tradeStaus) {
		for(PayResultStatus status : PayResultStatus.values()) {
			if(status.name().equals(tradeStaus)) {
				return status;
			}
		}
		return null;
	}
	
	
}

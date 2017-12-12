package com.noone.paymentms.biz.pay.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class PayUtil {

	private static PayUtil INSTANCE = new PayUtil();

	String reqUrl = SwiftpassConfig.req_url;

	private PayUtil() {
	}

	public static PayUtil getInstance() {
		return INSTANCE;
	}

	public PayResultStatus pay(String authCode, String orderNum, String totalFee) {
		Map<String, String> map = buildRequestPayMap(authCode, orderNum, totalFee);
		PayResultStatus result = call(map);
		return result;
	}

	public PayResultStatus queryPayStatus(String orderNum) {
		Map<String, String> map = buildRequestQueryPayMap(orderNum);
		PayResultStatus result = call(map);
		return result;
	}

	private PayResultStatus call(Map<String, String> requestMap) {
		PayResultStatus result = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		try {
			HttpPost httpPost = new HttpPost(reqUrl);
			StringEntity entityParams = new StringEntity(XmlUtils.parseXML(requestMap), "utf-8");
			httpPost.setEntity(entityParams);
			httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
			client = HttpClients.createDefault();
			response = client.execute(httpPost);
			if (response != null && response.getEntity() != null) {
				Map<String, String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
				System.out.println("支付请求返回参数：" + resultMap);
				System.out.println(resultMap.containsKey("sign"));

				String tradeStaus = resultMap.get("trade_state");

				if (resultMap.containsKey("sign") && !SignUtils.checkParam(resultMap, SwiftpassConfig.key)) {
					System.out.print("签名失败");
				} else {
					result = PayResultStatus.valueOf(tradeStaus);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private String getSign(Map<String, String> map) {
		Map<String, String> params = SignUtils.paraFilter(map);

		StringBuilder buf = new StringBuilder((params.size() + 1) * 10);

		SignUtils.buildPayParams(buf, params, false);

		String preStr = buf.toString();

		String sign = MD5.sign(preStr, "&key=" + SwiftpassConfig.key, "utf-8");

		return sign;
	}

	private String getRandomStr() {
		Calendar c = Calendar.getInstance();
		Random randomlong = new Random(16);
		return c.getTimeInMillis() + "" + randomlong.nextInt();
	}

	private Map<String, String> buildRequestPayMap(String authCode, String orderNum, String totalFee) {
		Map<String, String> map = new HashMap<>();
		map.put("service", "unified.trade.micropay");
		map.put("auth_code", authCode); // 二维码的code
		map.put("mch_id", SwiftpassConfig.mch_id); // 商户号
		map.put("out_trade_no", orderNum); // 商户生成的订单号
		map.put("total_fee", totalFee);

		map.put("body", "周昌盛面包");
		map.put("mch_create_ip", "127.0.1");
		map.put("nonce_str", getRandomStr()); // 随机字符串32

		String sign = getSign(map);
		map.put("sign", sign);// 签名

		System.out.println("buildRequestPayMap请求参数:" + XmlUtils.parseXML(map));

		return map;
	}

	private Map<String, String> buildRequestQueryPayMap(String orderNum) {
		Map<String, String> map = new HashMap<>();
		map.put("service", "unified.trade.query");
		map.put("mch_id", SwiftpassConfig.mch_id); // 商户号
		map.put("out_trade_no", orderNum); // 商户生成的订单号
		map.put("nonce_str", getRandomStr()); // 随机字符串32

		String sign = getSign(map);
		map.put("sign", sign);// 签名

		System.out.println("buildRequestQueryPayMap请求参数:" + XmlUtils.parseXML(map));

		return map;
	}
}

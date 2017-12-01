package com.noone.paymentms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noone.paymentms.domain.Account;

@RestController
public class HomeController extends BaseController {

//	@RequestMapping("/hello")
//	public Account hello() {
//		System.out.println("hello");
//		Account account = new Account();
//		account.setAccountName("account name xxx");
//		account.setAccountStatus("status");
//		return account;
//	}
	
	
	@RequestMapping("/hello")
	public String hello() {
		System.out.println("hello");
		Account account = new Account();
		account.setAccountName("account name xxx");
		account.setAccountStatus("status");
		return "hello world !";
	}
	
	
}
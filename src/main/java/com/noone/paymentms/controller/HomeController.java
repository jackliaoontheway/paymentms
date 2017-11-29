package com.noone.paymentms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController extends BaseController {

	@RequestMapping("/hello")
	public String hello() {
		System.out.println("hello");
		return "hello world!";
	}
	
	
}
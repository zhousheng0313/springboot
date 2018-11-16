package com.zzcx.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzcx.springboot.domain.User;

@RestController
public class TestController {

	@RequestMapping("hello")
	public String index() {
		return "Hello Spring Boot";
	}
	@RequestMapping("pojo")
	public User user() {
		User user = new User();
		user.setName("李四");
		return user;
	}
}

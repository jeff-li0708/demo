package com.liangzai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liangl on 2019/10/28.
 */
//@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	UserService userService;

	@Override
	public void addOrder() {
		System.out.println("------------------add order---------------------->");
	}
}

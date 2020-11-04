package com.liangzai.service;

import org.springframework.stereotype.Service;

/**
 * Created by liangl on 2019/10/28.
 */
//@Service
public class NewOrderServiceImpl implements OrderService{
	@Override
	public void addOrder() {
		System.out.println("------------new add order------------------>");
	}
}

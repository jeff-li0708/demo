package com.liangzai.service;

import com.liangzai.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liangl on 2019/10/23.
 */
//@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserDao userDao;

	@Autowired
	List<OrderService> orderServices;

	@Resource
	PayServiceImpl payService;

	@Override
	public void queryUser() {
		System.out.println("service query user result:"+userDao.query());
	}
}

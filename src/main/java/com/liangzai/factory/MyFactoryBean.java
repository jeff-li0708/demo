package com.liangzai.factory;

import com.liangzai.handler.MyInvocationHandler;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * Created by liangl on 2019/9/18.
 *
 * 如何将对象交给Spring管理?
 *
 * 通过FactoryBean
 */
public class MyFactoryBean implements FactoryBean {

	Class mappingInterface; //需要代理的类
	String c2;

	public MyFactoryBean() {
	}

	public MyFactoryBean(Class mappingInterface) {
		this.mappingInterface = mappingInterface;
	}
	public MyFactoryBean(String c2) {
		this.c2 = c2;
	}

	public MyFactoryBean(Class mappingInterface, String c2) {
		this.mappingInterface = mappingInterface;
		this.c2 = c2;
	}

	/**
	 * 重重重点：
	 * 将一个FactoryBean注册到spring容器后，当通过applicationContext.getBean("myFactoryBean"),
	 * 获取到的对象是getObjec()方法返回的对象，只有applicationContext.getBean("&myFactoryBean")这种方式返回的才是MyFactoryBean类的对象
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getObject() throws Exception {
		//利用JDK的动态代理创建个代理对象
		return Proxy.newProxyInstance(mappingInterface.getClassLoader(),new Class[]{mappingInterface},new MyInvocationHandler());
	}

	@Override
	public Class<?> getObjectType() {
		return mappingInterface;
	}
}

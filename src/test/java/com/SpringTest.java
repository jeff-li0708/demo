package com;


import com.liangzai.bean.User;
import com.liangzai.config.AppConfig;
import com.liangzai.dao.UserDao;
import com.liangzai.service.A;
import com.liangzai.service.D;
import com.liangzai.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by liangl on 2019/9/5.
 */
public class SpringTest {


	public static void main(String[] args) throws InterruptedException {

		//通过Java代码方式创建应用上下文
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(); //
		//注册一个或多个带注解的类
		applicationContext.register(AppConfig.class);

		applicationContext.refresh();
		D a = (D) applicationContext.getBean("d");
		for(int i=0;i<100;i++){
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
//					try {
//						Thread.sleep(4999);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
					a.getUser();
				}
			},"th:----->"+i);
			thread.start();
		}

		UserService userService = applicationContext.getBean(UserService.class);
		userService.queryUser();
		//获取通过注解Bean注册的对象
		User user = (User) applicationContext.getBean("user");
		System.out.println(user.getName());

		//获取接口对象
		UserDao userDao = (UserDao) applicationContext.getBean("userDao");


		System.out.println(userDao.query());
	}


}

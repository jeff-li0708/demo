package com.liangzai.register;

import com.liangzai.dao.UserDao;
import com.liangzai.factory.MyFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangl on 2019/9/18.
 */
public class MyImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        /***************仿照Mybatis的MapperScan，实现多个Dao接口的注册*****************/
        //1.拿到注解
		AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MyMapperScan.class.getName()));

		//2.拿到需要扫描的包
		List<String> basePackages = new ArrayList<String>();
		for (String pkg : annoAttrs.getStringArray("value")) {
			if (StringUtils.hasText(pkg)) {
				basePackages.add(pkg);
			}
		}

		//3.拿到包下所有的Dao接口的名字,for循环注册到Srping容器/////难得实现，下面写死个作为例子
		 /************end***********************************/

		BeanDefinitionBuilder beanDefinitionBuilder =
				BeanDefinitionBuilder.genericBeanDefinition(MyFactoryBean.class); //手动注册一个BeanDefinition到Spring容器中,这里注册的是MyFactoryBean

		GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionBuilder.getBeanDefinition();
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserDao.class); //设置MyFactoryBean.mappingInterface
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue("com.UserDao");

		//让FactoryBean生效
		registry.registerBeanDefinition("userDao",beanDefinition); //这里注册的是BeanDefinition的beanClass是MyFactoryBean，BeanDefinitionMap的key为userDao

	}
}

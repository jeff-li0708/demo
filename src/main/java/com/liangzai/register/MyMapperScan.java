package com.liangzai.register;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liangl on 2019/9/18.
 *
 * Mybatis使用MapperScan注解配置要扫描的包,下面是仿照的一个简单实现
 *
 *
 * JAVA 中有以下几个『元注解』：
 * @Target：注解的作用目标
 * @Retention：注解的生命周期
 * @Documented：注解是否应当被包含在 JavaDoc 文档中
 * @Inherited：是否允许子类继承该注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MyImportBeanDefinitionRegister.class)
public @interface MyMapperScan {

	String[] value() default {};
}

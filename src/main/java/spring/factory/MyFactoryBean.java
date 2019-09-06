package spring.factory;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * Created by liangl on 2019/9/5.
 */
public class MyFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> mapperInterface;

    public MyFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{mapperInterface},new MyInvocationHandler());
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}

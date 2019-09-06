package spring.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by liangl on 2019/9/5.
 */
public class MyInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(111);
        return null;
    }
}

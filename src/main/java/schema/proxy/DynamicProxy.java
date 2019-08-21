package schema.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理（基于接口）
 * 打印出目标方法执行的开始时间和结束时间
 * 在java的java.lang.reflect包下提供了一个Proxy类和一个InvocationHandler接口，通过这个类和这个接口可以生成JDK动态代理类和动态代理对象
 * Created by liangl on 2019/3/4.
 */
public class DynamicProxy {

    interface Person {
        String sellHouse();
    }

    class Boss implements Person {

        @Override
        public String sellHouse() {
            System.out.println("我要卖房子");
            return "完成";
        }
    }

    class MyInvocationHandler<T extends Person> implements InvocationHandler {

        private T target;

        public MyInvocationHandler(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("start:"+System.currentTimeMillis());
            Object result = method.invoke(target, args);
            System.out.println("end:"+System.currentTimeMillis());
            return result;
        }
    }


    public static void main(String[] args) {
        new DynamicProxy().run();
    }
    public void run(){
        MyInvocationHandler invocationHandler = new MyInvocationHandler(new Boss());
        //创建一个代理对象bossProxy来代理boss，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
        Person bossProxy = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class<?>[]{Person.class}, invocationHandler);

        Object result = bossProxy.sellHouse();
        System.out.println(result);
    }
}

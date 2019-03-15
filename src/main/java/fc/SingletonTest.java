package fc;

import java.io.Serializable;

/**
 * 单例
 * Created by liangl on 2019/2/28.
 */
public class SingletonTest implements Serializable{
    //1.私有构造方法
    private SingletonTest(){}
    //2.使用内部类维护单例
    public static class SingletonFactory {
        private static SingletonTest singleton = new SingletonTest();
    }
    //3.获取实例
    public static SingletonTest getSingletonInstance(){
        return SingletonFactory.singleton;
    }

    //4.如果该对象被用于序列化，可以保证对象在序列化前后保持一致
    public Object readResolve() {
        return getSingletonInstance();
    }
}

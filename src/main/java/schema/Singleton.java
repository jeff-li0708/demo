package schema;

import java.io.Serializable;

/**
 * 单例
 * Created by liangl on 2019/2/28.
 */
public class Singleton implements Serializable{
    //1.私有构造方法
    private Singleton(){}
    //2.使用内部类维护单例
    public static class SingletonFactory {
        private static Singleton singleton = new Singleton();
    }
    //3.获取实例
    public static Singleton getSingletonInstance(){
        return SingletonFactory.singleton;
    }

    //4.如果该对象被用于序列化，可以保证对象在序列化前后保持一致
    public Object readResolve() {
        return getSingletonInstance();
    }
}

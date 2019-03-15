package util;

public class ContextUtils {

    final static ThreadLocal local=new InheritableThreadLocal();

    public static <T> void set(T object){
        local.set(object);
    }

    public static <T> T get(){
        return (T)local.get();
    }

    public static void remove(){
        local.remove();
    }
}

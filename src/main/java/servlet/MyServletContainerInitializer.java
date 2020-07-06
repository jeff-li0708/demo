package servlet;


import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * 自定义一个ServletContainerInitializer的实现类
 * 容器在启动应用的时候，会扫描当前应用每一个jar包里面META-INF/services/javax.servlet.ServletContainerInitializer指定的实现类，启动并运行这个实现类的onStartup
 * @HandlesTypes 注解可以指定匹配的类型，在运行时将其实例放入onStartup的第一个参数中
 */

public class MyServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("this is MyServletContainerInitializer");
    }
}

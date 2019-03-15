package fc;

import bean.Student;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by liangl on 2019/3/11.
 */
public class ReflectTest {

    public static void main(String[] args) {
        Class clazz = Student.class;
        clazz.getMethods();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method:methods){
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                String name = parameter.getName();
                System.out.println(name);
            }
        }
    }
}

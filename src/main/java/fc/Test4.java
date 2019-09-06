package fc;

import bean.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by liangl on 2019/3/25.
 */
public class Test4 {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        Student stu1 = new Student();
        stu1.setName("test1");
        stu1.setAge(1);
        list.add(stu1);

        Student stu2 = new Student();
        stu2.setName("test2");
        stu2.setAge(2);
        list.add(stu2);

        Student stu4 = new Student();
        stu4.setName("test1");
        stu4.setAge(4);
        list.add(stu4);

        Student stu3 = new Student();
        stu3.setName("test1");
        stu3.setAge(3);
        list.add(stu3);


        Map<String, Student> stuMap = list.stream().collect(Collectors.toMap(Student::getName, Function.identity(),(v1, v2)-> v1.getAge().compareTo(v2.getAge()) >= 0 ? v1:v2));

        System.out.println(stuMap);

    }
}

package spring.dao;

import org.apache.ibatis.annotations.Select;

/**
 * Created by liangl on 2019/9/5.
 */
public interface UserDao {

    @Select(value = "select * from user")
    String query();
}

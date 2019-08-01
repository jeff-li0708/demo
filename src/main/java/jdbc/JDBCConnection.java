package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by liangl on 2019/8/1.
 */
public class JDBCConnection {

    public static void main(String[] args) {

         String driver="com.mysql.jdbc.Driver";//驱动路径
         String url="jdbc:mysql://localhost:3306/eshop";//数据库地址
         String user="root";//访问数据库的用户名
         String password="123456";//用户密码
         try {
             //1、加载驱动
             Class.forName(driver);
             //2、链接数据库
             Connection con = DriverManager.getConnection(url, user, password);
             if (!con.isClosed()) {        //判断数据库是否链接成功
                 System.out.println("已成功链接数据库！");
                 //3、创建Statement对象
                 Statement st = con.createStatement();
                 //4、执行sql语句
                 String sql="select * from user";//查询user表的所有信息
                 ResultSet rs = st.executeQuery(sql);//查询之后返回结果集
                 //5、打印出结果
                 while(rs.next()){
                     System.out.println(rs.getString("Id")+"\t"+rs.getString("name")+"\t"+rs.getString("password"));
                 }
                 rs.close();//关闭资源
                 con.close();//关闭数据库
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
     }
}

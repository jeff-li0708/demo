package tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import servlet.UserServlet;

import java.io.File;

/**
 * Created by liangl on 2019/9/12.
 */
public class TestMain {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);//设置端口
        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp("", getAbsolutePath() +  "src/main/webapp");
        //tomcat.addServlet("/access","userServlet",new UserServlet());// 注册servlet
        tomcat.start();
        tomcat.getServer().await();//维持tomcat服务，否则tomcat一启动就会关闭
    }


    private static String getAbsolutePath() {
        String path = null;
        String folderPath = TestMain.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                .substring(1);
        if (folderPath.indexOf("target") > 0) {
            path = folderPath.substring(0, folderPath.indexOf("target"));
        }
        return path;
    }

}

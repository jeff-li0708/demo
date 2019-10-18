package tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * Created by liangl on 2019/9/12.
 */
public class TestMain {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp("/javatool-server", getAbsolutePath() + "src/main/webapp");
        tomcat.start();
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

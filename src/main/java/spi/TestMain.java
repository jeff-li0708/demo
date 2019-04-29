package spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Java spi技术
 * 1.接口和实现的定义
 * 2.在META-INF文件夹下创建了services文件夹，并在services文件夹下创建了一个文件，文件名以接口的全限定名来命名
 * Created by liangl on 2019/4/25.
 */
public class TestMain {
    public static void main(String[] args) {
        ServiceLoader<GameService> spiLoader = ServiceLoader.load(GameService.class);
        Iterator<GameService> iteratorSpi = spiLoader.iterator();
        while (iteratorSpi.hasNext()) {
            GameService gameService = iteratorSpi.next();
            gameService.sayHello();
        }
    }
}

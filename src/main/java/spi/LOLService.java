package spi;

/**
 * Created by liangl on 2019/4/25.
 */
public class LOLService implements GameService{
    @Override
    public void sayHello() {
        System.out.println("I'm LOL");
    }
}

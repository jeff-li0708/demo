package spi;

/**
 * Created by liangl on 2019/4/25.
 */
public class CFService implements GameService{
    @Override
    public void sayHello() {
        System.out.println("I'm CF");
    }
}

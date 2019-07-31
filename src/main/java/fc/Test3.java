package fc;

/**
 * Created by liangl on 2019/3/25.
 */
public class Test3 {
    public static void main(String[] args) {

        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MIN_VALUE);
        System.out.println(Long.toBinaryString(Long.MAX_VALUE));
        Long a = 0B111111111111111111111111111111111111111111111111111111111111111L;
        System.out.println(a);
        Long.parseLong("111111111111111111111111111111111111111111111111111111111111111", 2);
    }
}

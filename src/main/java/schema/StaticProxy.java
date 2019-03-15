package schema;

/**
 * 静态代理
 * Created by liangl on 2019/3/4.
 */
public class StaticProxy {
    //1.定义目标接口
    interface Person {
        void sellHouse();
    }

    //目标对象
    class Boss implements Person {

        @Override
        public void sellHouse() {
            System.out.println("我想卖房子");
        }
    }

    //代理
    class ProxyPerson implements Person {
        private Boss boss;

        public ProxyPerson(Boss boss) {
            this.boss = boss;
        }

        @Override
        public void sellHouse() {
            beforeDoSth();
            boss.sellHouse();
        }

        public void beforeDoSth(){
            System.out.println("你需要准备好房产证");
        }
    }


    public static void main(String[] args) {
        new StaticProxy().run();
    }

    public void run() {
        new ProxyPerson(new Boss()).sellHouse();
    }
}

package schema.observer;

/**
 * 具体观察者
 */
public class ConcreteObserver implements Observer {

    @Override
    public void dealNotice(String state) {
        System.out.println("我被通知了，我要改变状态为：" + state);
    }
}

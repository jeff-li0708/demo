package schema.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体主题
 */
public class ConcreteSubject implements Subject {

    private List<Observer> observerList = new ArrayList<>();
    private String state;

    @Override
    public void attach(Observer observer) {
        this.observerList.add(observer);
        System.out.println("向ConcreteSubject注册了一个观察者");
    }

    @Override
    public void detach(Observer observer) {
        this.observerList.remove(observer);
        System.out.println("从ConcreteSubject移除了一个观察者");
    }

    @Override
    public void notifyObservers() {
        this.observerList.forEach(observer -> observer.dealNotice(this.state));
    }

    public void changeState(String state) {
        this.state = state;
        this.notifyObservers();
    }
}

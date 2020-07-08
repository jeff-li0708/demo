package schema.observer;

/**
 * 抽象观察者
 * 提供具体观察者需要实现的接口
 */
public interface Observer {

    /**
     *
     */
    void dealNotice(String state);
}

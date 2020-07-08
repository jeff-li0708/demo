package schema.observer;

/**
 * 抽象主题
 * 提供具体主题需要实现的接口
 */
public interface Subject {

    /**
     * 注册观察者
     * @param observer
     */
    void attach(Observer observer);

    /**
     *  移除观察者
     *  @param observer
     */
    void detach(Observer observer);

    /**
     * 通知所有注册的观察者
     */
    void notifyObservers();
}

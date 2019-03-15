package bean;

/**
 * Created by liangl on 2019/3/11.
 */
public class Product {
    private int value;
    private int weight;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return weight +"->" + value;
    }
}

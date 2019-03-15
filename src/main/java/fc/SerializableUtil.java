package fc;

import java.io.*;

/**
 * Created by liangl on 2019/2/28.
 */
public class SerializableUtil<T extends Serializable> {

    /**
     * 将对象序列化后存文件
     * @throws IOException
     */
    public T serializableObj(T t) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("F:/serializable.txt")));
        oos.writeObject(t);
        oos.close();
        return t;
    }

    /**
     * 反序列化
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public T deserializeObj() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("F:/serializable.txt")));
        T t = (T) ois.readObject();
        return t;
    }
}

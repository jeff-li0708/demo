package util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangl on 2019/4/8.
 */
public class BeanUtil {

    public static Map<String,String> beanToMap(Object bean) {
        Map<String, String> param = new HashMap<>();
        try {
            param = BeanUtils.describe(bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return param;
    }
}

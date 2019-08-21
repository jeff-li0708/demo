package schema.strategy;

import org.springframework.stereotype.Service;

/**
 * Created by liangl on 2019/8/21.
 */
@Service
public class ZFBPayServiceImpl implements PayService{
    @Override
    public boolean pay(String json) {
        System.out.println("ali pay:"+json);
        return true;
    }
}

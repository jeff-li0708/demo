package fc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by liangl on 2019/7/8.
 */
public class ThreadPoolTest {
    //static ExecutorService es = Executors.newFixedThreadPool(50);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolTest a = new ThreadPoolTest();
        final List<String> showIdList = new ArrayList<>();
        for (int i = 0;i<50;i++) {
            showIdList.add("test-"+i);
        }
        for (int i = 0;i <3;i++) {

            Thread aa = new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONArray arr = a.test(showIdList);
                    System.out.println(JSON.toJSONString(arr));
                }
            },"thread"+i);
            aa.start();
        }

    }

    public JSONArray test(List<String> showIdList)  {
        try {
            List<Callable<JSONObject>> taskList = new ArrayList<>();
            for (String showId: showIdList){
                taskList.add(new QueryVideoAuthorCommentCount(showId));
            }

            List<Future<JSONObject>> result = null;
            ExecutorService es = new ThreadPoolExecutor(10,50,200, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());
            try {
                result = es.invokeAll(taskList);
            } catch (InterruptedException e) {
                System.out.println("eeeeeeeeeeeeeeeee");
                return null;
            } finally {
                es.shutdown();
            }

            if (result!=null && !result.isEmpty()) {
                JSONArray array = new JSONArray();
                for (Future<JSONObject> jsonFuture : result) {
                    array.add(jsonFuture.get());
                }
                return array;
            }
        } catch (Exception e) {

        }

        return null;
    }
    class QueryVideoAuthorCommentCount implements Callable<JSONObject>{
        String showId;

        public QueryVideoAuthorCommentCount(String showId){
            this.showId = showId;
        }
        @Override
        public JSONObject call() {
            return queryVideoAuthorCommentCount(showId);
        }
    }

    public JSONObject queryVideoAuthorCommentCount(String showId) {
        JSONObject param = new JSONObject();
        param.put("showId", showId);
        System.out.println(Thread.currentThread().getName()+"---"+ 111);
        try {
            Thread.sleep(300);
            if (Integer.valueOf(showId.split("-")[1]) % 10 == 1) {
                throw  new RuntimeException();
            }
            return param;
        } catch (Exception e) {
            return null;
        }
    }
}

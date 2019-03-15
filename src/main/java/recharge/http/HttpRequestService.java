package recharge.http;

import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xinz on 2017/3/30.
 */
public abstract class HttpRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestService.class);

    int TIME_OUT = 30000;   //超时时间
    int POOL_SIZE = 100;    //连接池大小
    int KEEP_ALIVE_DURATION = 30000;  //连接池任务丢弃超时时间
    protected OkHttpClient client ;

    public HttpRequestService(int TIME_OUT) {
        this.TIME_OUT = TIME_OUT;
        init();
    }

    public HttpRequestService() {
        init();
    }

    public HttpRequestService(int TIME_OUT, int POOL_SIZE) {
        this.TIME_OUT = TIME_OUT;
        this.POOL_SIZE = POOL_SIZE;
        this.KEEP_ALIVE_DURATION = TIME_OUT;
        init();
    }


    private void init(){
        try {
            LOGGER.info("HttpRequestService.init http client pool start ...");
            ConnectionPool connectionPool = new ConnectionPool(POOL_SIZE,KEEP_ALIVE_DURATION, TimeUnit.MILLISECONDS);
            client = new OkHttpClient().newBuilder()
                    .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            //强行返回true 即验证成功
                            return true;
                        }
                    })
                    .connectionPool(connectionPool).build();
            LOGGER.info("HttpRequestService.init http client pool finish ...");
        }catch (Throwable throwable){
            LOGGER.error("HttpRequestService.init http client pool error ", throwable);
            throw new RuntimeException("HttpRequestService init fail");
        }
    }

    /*
    post form 请求
     */
    public abstract String postForm(String url,Map<String,String> params) throws IOException;

    public abstract String postFormByHeader(String url, Map<String, String> params, Request.Builder rebuilder) throws IOException;

    /*
    post 请求 写body   params 会以 参数1=value1&参数2=value2    进行传输
     */
    public abstract String postBody(String url,Map<String,String> params) throws IOException;

    /*
    post 请求 写body   bodyParam直接写入body
     */
    public abstract String postBody(String url,String bodyParam) throws IOException;

    /*
    post 请求 写body   bodyParam直接写入body， mediaType指定对应httprequest content
     */
    public abstract String postBody(String url,String bodyParam,MediaType mediaType) throws IOException;

    /*
    get 请求 默认UTF-8 编码
     */
    public abstract String get(String url,Map<String,String> parmas) throws IOException;
    public abstract String getByProxy(String url,Map<String,String> parmas) throws IOException;

    public abstract String get(String url) throws IOException;

    public abstract String doJsonGet(String url) throws IOException;
    public abstract String doJsonGet(String url,Map<String, String> parmas) throws IOException;

    /*
    以下接口对结果进行JSON序列化
     */
    public abstract <T> T postForm(String url,Map<String,String> params,Class<T> tClass) throws IOException;

    public abstract <T> T postBody(String url,Map<String,String> params,Class<T> tClass) throws IOException;

    public abstract <T> T get(String url,Map<String,String> parmas,Class<T> tClass) throws IOException;

    public abstract <T> T postBody(String url,String bodyParam,Class<T> tClass) throws IOException;

    public abstract <T> T postBody(String url,String bodyParam,Class<T> tClass,MediaType mediaType) throws IOException;

    public abstract byte[] getFile(String url,Map<String,String> params) throws IOException;

    abstract byte[] getFileByProxy(String url, Map<String, String> params) throws IOException;

    public abstract String requestUploadImage(String requestUrl, Map<String, String> paramsMap,byte[] image) throws IOException;

    public abstract String requestUploadImage(String requestUrl, Map<String, String> paramsMap,byte[] image,String fileParamName) throws IOException;

    /**
     *
     * @param requestUrl
     * @param paramsMap
     * @param image
     * @param fileParamName 图片form参数名称
     * @param fileName 文件名称
     * @return
     * @throws java.io.IOException
     */
    public abstract String requestUploadImage(String requestUrl, Map<String, String> paramsMap,byte[] image,String fileParamName,String fileName) throws IOException;

}

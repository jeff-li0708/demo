package recharge.http;

import com.alibaba.fastjson.JSON;
import okhttp3.MediaType;
import okhttp3.Request;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

/**
 * gate way http 请求工具
 * Created by xinz on 2017/3/30.
 */
@Service
public class SimpleHttpService extends HttpRequestService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SimpleHttpService.class);

    private MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//默认mediaType

    Map empty = Collections.emptyMap();


    public SimpleHttpService() {
        //todo 可指定okhttp相关配置，否则走默认
//        super(TIME_OUT);
    }

    @Override
    public String postForm(String url, Map<String, String> params) throws IOException {
        return HttpHelper.requestPostWithForm(url, params, client);
    }

    @Override
    public String postFormByHeader(String url, Map<String, String> params,Request.Builder rebuilder) throws IOException {
        return HttpHelper.requestPostWithForm(url, params, client,rebuilder);
    }


    @Override
    public String postBody(String url, Map<String, String> params) throws IOException {
        return HttpHelper.requestPost(url, formatBodyParams(params), mediaType, client);
    }

    @Override
    public String postBody(String url, String bodyParam) throws IOException {
        return HttpHelper.requestPost(url, bodyParam, mediaType, client);
    }

    @Override
    public String postBody(String url, String bodyParam, MediaType mediaType) throws IOException {
        return HttpHelper.requestPost(url, bodyParam, mediaType, client);
    }

    @Override
    public String get(String url, Map<String, String> parmas) throws IOException {
        return HttpHelper.requestGet(url, parmas, client);
    }

    @Override
    public String getByProxy(String url, Map<String, String> parmas) throws IOException {
        return HttpHelper.requestGetByProxy(url, parmas);
    }

    @Override
    public String get(String url) throws IOException {
        return get(url,empty);
    }

    @Override
    public String doJsonGet(String url) throws IOException {
        return HttpHelper.doJSONGet(url, empty, client);
    }

    @Override
    public String doJsonGet(String url,Map<String, String> parmas) throws IOException {
        return HttpHelper.doJSONGet(url, parmas, client);
    }


    @Override
    public <T> T postForm(String url, Map<String, String> params, Class<T> tClass) throws IOException {
        String result = HttpHelper.requestPostWithForm(url, params, client);
        logger.debug("body post url:{} bodyParams:{} return value:{}",url,params,result);
        return JSON.parseObject(result, tClass);
    }

    @Override
    public <T> T postBody(String url, Map<String, String> params, Class<T> tClass) throws IOException {
        String result =  HttpHelper.requestPost(url, formatBodyParams(params), mediaType, client);
        logger.debug("body post url:{} bodyParams:{} return value:{}",url,params,result);
        return JSON.parseObject(result,tClass);
    }

    @Override
    public <T> T get(String url, Map<String, String> parmas, Class<T> tClass) throws IOException {
        String result = HttpHelper.requestGet(url, parmas, client);
        return JSON.parseObject(result,tClass);
    }


    @Override
    public <T> T postBody(String url, String bodyParam, Class<T> tClass) throws IOException {
        String result = HttpHelper.requestPost(url, bodyParam, mediaType, client);
        return JSON.parseObject(result,tClass);
    }

    @Override
    public <T> T postBody(String url, String bodyParam, Class<T> tClass, MediaType mediaType) throws IOException {
        String result = HttpHelper.requestPost(url, bodyParam, mediaType, client);
        return JSON.parseObject(result,tClass);
    }


    private String formatBodyParams(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : params.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params.get(key), "utf-8")));
            pos++;
        }

        //生成参数
        return tempParams.toString();
    }

    @Override
    public byte[] getFile(String url, Map<String, String> params) throws IOException {
        return HttpHelper.requestGetFile(url, params, client);
    }

    @Override
    public byte[] getFileByProxy(String url, Map<String, String> params) throws IOException {
        return HttpHelper.requestGetFileByProxy(url, params, client);
    }

    public static void main(String[] args) throws IOException {

    }

    @Override
    public String requestUploadImage(String requestUrl, Map<String, String> paramsMap, byte[] image) throws IOException {
        return HttpHelper.requestUploadImage(requestUrl,paramsMap,image,client);
    }


    @Override
    public String requestUploadImage(String requestUrl, Map<String, String> paramsMap, byte[] image, String fileParamName) throws IOException {
        return HttpHelper.requestUploadImage(requestUrl,paramsMap,image,client,fileParamName);
    }

    @Override
    public String requestUploadImage(String requestUrl, Map<String, String> paramsMap, byte[] image, String fileParamName, String fileName) throws IOException {
        return HttpHelper.requestUploadImage(requestUrl,paramsMap,image,client,fileParamName,fileName);
    }
}

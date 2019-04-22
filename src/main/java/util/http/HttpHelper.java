package util.http;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * http 调用工具类
 * Created by liangl on 2019/3/22.
 */
public class HttpHelper {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    static int TIME_OUT = 10000;   //超时时间
    static int POOL_SIZE = 100;    //连接池大小
    static int KEEP_ALIVE_DURATION = 30000;  //连接池任务丢弃超时时间
    static protected OkHttpClient client ;

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/xml; charset=gbk");

    static{
        try {
            ConnectionPool connectionPool = new ConnectionPool(POOL_SIZE,KEEP_ALIVE_DURATION, TimeUnit.MILLISECONDS);
            client = new OkHttpClient().newBuilder()
                    .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .connectionPool(connectionPool).build();
        }catch (Throwable throwable){
            throw new RuntimeException("HttpRequestService init fail");
        }
    }



    public static Request.Builder addHeaders() {
        return new Request.Builder()
                .addHeader("Connection", "keep-alive");
    }

    private static StringBuilder paramsToStr(Map<String,String> paramsMap) throws UnsupportedEncodingException {
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
            pos++;
        }
        return tempParams;
    }

    /**
     * GET 请求
     * @param url
     * @param paramsMap
     * @return
     * @throws IOException
     */
    public static byte[] requestGetFile(String url, Map<String, String> paramsMap) throws IOException {
        StringBuilder temp = paramsToStr(paramsMap);

        String requestUrl;
        if(!paramsMap.isEmpty()){
            requestUrl = String.format("%s?%s", url, temp.toString());
        }
        else{
            requestUrl = url;
        }
        long span = System.currentTimeMillis();
        Request request = addHeaders().url(requestUrl).build();

        final Call call = client.newCall(request);
        final Response response = call.execute();

        if (response.isSuccessful()) {
            return response.body().bytes();
        }
        throw new IOException("http response error " + response.code() + " " + response.message());
    }


    /**
     * GET 请求
     * @param url
     * @param paramsMap
     * @return
     * @throws IOException
     */
    public static byte[] postRequestGetFile(String url, Map<String, String> paramsMap) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            builder.add(key, paramsMap.get(key));
        }
        RequestBody formBody = builder.build();

        long span=System.currentTimeMillis();

        final Request request = addHeaders().url(url).post(formBody).build();
        final Call call = client.newCall(request);
        final Response response = call.execute();
        if (response.isSuccessful()) {
            return response.body().bytes();
        }
        throw new IOException("http response error " + response.code() + " " + response.message());
    }


    /**
     * POST 请求
     * @param url
     * @param paramsMap
     * @return
     * @throws IOException
     */
    public static byte[] postByBodyGetFile(String url, Map<String, Object> paramsMap, MediaType mediaType) throws IOException {
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(paramsMap));
        long span=System.currentTimeMillis();
        final Request request = addHeaders().url(url).post(body).build();
        final Call call = client.newCall(request);
        final Response response = call.execute();
        if (response.isSuccessful()) {
            return response.body().bytes();
        }
        throw new IOException("http response error " + response.code() + " " + response.message());
    }


    /**
     * form post 请求
     * @param requestUrl
     * @param paramsMap
     * @return
     * @throws IOException
     */
    public static String requestPostWithForm(String requestUrl, Map<String, String> paramsMap) throws IOException {

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            builder.add(key, paramsMap.get(key));
        }
        RequestBody formBody = builder.build();

        long span=System.currentTimeMillis();

        final Request request = addHeaders().url(requestUrl).post(formBody).build();
        final Call call = client.newCall(request);

        Response response = call.execute();
        String ret="";
        if (response.isSuccessful()) {
            ret= response.body().string();
        }else{
            if(response.code()>=400&&response.code()<500){
                logger.info("http request 400 error:{}",response.body().source().readUtf8());
            }
        }
        if(response.isSuccessful()) {
            return ret;
        }

        throw new IOException("http response error "+response.code()+" "+response.message()+response.body().toString());
    }

    /**
     * GET 请求
     * @param url
     * @param paramsMap
     * @return
     * @throws IOException
     */
    public static String requestGet(String url, Map<String, String> paramsMap) throws IOException {
        StringBuilder tempParams = paramsToStr(paramsMap);

        String requestUrl;
        if(!paramsMap.isEmpty()){
            requestUrl = String.format("%s?%s", url, tempParams.toString());
        }
        else{
            requestUrl = url;
        }
        long span = System.currentTimeMillis();
        Request request = addHeaders().url(requestUrl).build();

        final Call call = client.newCall(request);
        final Response response = call.execute();

        String ret = "";
        if (response.isSuccessful()) {
            ret = response.body().string();
        }
        if (response.isSuccessful()) {
            return ret;
        }

        throw new IOException("http response error " + response.code() + " " + response.message());
    }

    /**
     * GET 请求
     * @param url
     * @param param
     * @return
     * @throws IOException
     */
    public static String requestGetByStrParamNotEncode(String url, String param) throws IOException {
        String requestUrl;
        if(!param.isEmpty()){
//            param = URLEncoder.encode(param, "utf-8");
            requestUrl = String.format("%s?%s", url, param);
        }
        else{
            requestUrl = url;
        }
        long span = System.currentTimeMillis();
        Request request = addHeaders().url(requestUrl).build();

        final Call call = client.newCall(request);
        final Response response = call.execute();

        String ret = "";
        if (response.isSuccessful()) {
            ret = response.body().string();
        }
        if (response.isSuccessful()) {
            return ret;
        }

        throw new IOException("http response error " + response.code() + " " + response.message());
    }

    /**
     * post 请求，写body
     * @param requestUrl
     * @param bodyContent
     * @param mediaType
     * @return
     * @throws IOException
     */
    public static String requestPost(String requestUrl, String bodyContent, MediaType mediaType) throws IOException {
        RequestBody body = RequestBody.create(mediaType, bodyContent);

        long span=System.currentTimeMillis();

        final Request request = addHeaders().url(requestUrl).post(body).build();
        final Call call = client.newCall(request);
        Response response = call.execute();

        String ret="";
        if (response.isSuccessful()) {
            ret =response.body().string();
        }

        if (response.isSuccessful()) {
            return ret;
        }

        throw new IOException("http response error "+response.code()+" "+response.message());
    }


    /**
     * form post 请求
     * @param requestUrl
     * @param paramsMap
     * @return
     * @throws IOException
     */
    public static String requestUploadImage(String requestUrl, Map<String, String> paramsMap,byte[] image) throws IOException {

        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), image);

        builder.addFormDataPart("image", UUID.randomUUID()+"", fileBody);

        for (String key : paramsMap.keySet()) {
            builder.addFormDataPart(key, paramsMap.get(key));
        }

        RequestBody requestBody = builder.build();
        long span=System.currentTimeMillis();

        final Request request = addHeaders().url(requestUrl).post(requestBody).build();
        final Call call = client.newCall(request);

        Response response = call.execute();
        String ret="";
        if (response.isSuccessful()) {
            ret= response.body().string();
        }
        if(response.isSuccessful()) {
            return ret;
        }

        throw new IOException("http response error "+response.code()+" "+response.message());
    }



}

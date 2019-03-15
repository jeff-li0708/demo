package recharge.http;

import okhttp3.*;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * http 调用工具类
 * Created by xinz on 2017/3/22.
 */
public class HttpHelper {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    public static Request.Builder addHeaders() {
        return new Request.Builder()
                .addHeader("Connection", "keep-alive");
    }

    public static Request.Builder addHeaders2() {
        return new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("x-requested-with","XMLHttpRequest");
    }

    /**
     * GET 请求
     * @param url
     * @param paramsMap
     * @param okHttpClient
     * @return
     * @throws java.io.IOException
     */
    public static byte[] requestGetFile(String url, Map<String, String> paramsMap, OkHttpClient okHttpClient) throws IOException {
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
            pos++;
        }

        String requestUrl;
        if(!paramsMap.isEmpty()){
            requestUrl = String.format("%s?%s", url, tempParams.toString());
        }
        else{
            requestUrl = url;
        }
        long span = System.currentTimeMillis();
        Request request = addHeaders().url(requestUrl).build();

        final Call call = okHttpClient.newCall(request);
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
     * @param okHttpClient
     * @return
     * @throws java.io.IOException
     */
    public static byte[] requestGetFileByProxy(String url, Map<String, String> paramsMap, OkHttpClient okHttpClient) throws IOException {
        StringBuilder tempParams = new StringBuilder();
        ConnectionPool connectionPool = new ConnectionPool(100,30000, TimeUnit.MILLISECONDS);
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .writeTimeout(30000, TimeUnit.MILLISECONDS)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.17.108", 8118)))
                .connectionPool(connectionPool).build();
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
            pos++;
        }

        String requestUrl;
        if(!paramsMap.isEmpty()){
            requestUrl = String.format("%s?%s", url, tempParams.toString());
        }
        else{
            requestUrl = url;
        }
        long span = System.currentTimeMillis();
        Request request = addHeaders().url(requestUrl).build();

        final Call call = okHttpClient.newCall(request);
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
     * @param okHttpClient
     * @return
     * @throws java.io.IOException
     */
    public static String requestPostWithForm(String requestUrl, Map<String, String> paramsMap,OkHttpClient okHttpClient) throws IOException {

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            builder.add(key, paramsMap.get(key));
        }
        RequestBody formBody = builder.build();

        long span=System.currentTimeMillis();

        final Request request = addHeaders().url(requestUrl).post(formBody).build();
        final Call call = okHttpClient.newCall(request);

        Response response = call.execute();
        String ret="";
        if (response.isSuccessful()) {
            ret= response.body().string();
        }else{
            if(response.code()>=400&&response.code()<500){
                logger.info("http request 400 error:{}",response.body().source().readUtf8());
            }
        }
        logger.info("url:{},response:{}", requestUrl, ret);
        logRequest(requestUrl,paramsMap.toString(),ret,response.code(),span/1000,System.currentTimeMillis()-span );
        if(response.isSuccessful()) {
            return ret;
        }

        throw new IOException("http response error "+response.code()+" "+response.message());
    }

    /**
     * form post 请求
     * @param requestUrl
     * @param paramsMap
     * @param okHttpClient
     * @return
     * @throws java.io.IOException
     */
    public static String requestPostWithForm(String requestUrl, Map<String, String> paramsMap,OkHttpClient okHttpClient,Request.Builder rebuilder) throws IOException {

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            builder.add(key, paramsMap.get(key));
        }
        RequestBody formBody = builder.build();

        long span=System.currentTimeMillis();

        final Request request = rebuilder.url(requestUrl).post(formBody).build();
        final Call call = okHttpClient.newCall(request);

        Response response = call.execute();
        String ret="";
        if (response.isSuccessful()) {
            ret= response.body().string();
        }else{
            if(response.code()>=400&&response.code()<500){
                logger.info("http request 400 error:{}",response.body().source().readUtf8());
            }
        }
        logger.info("url:{},response:{}", requestUrl, ret);
        logRequest(requestUrl,paramsMap.toString(),ret,response.code(),span/1000,System.currentTimeMillis()-span );
        if(response.isSuccessful()) {
            return ret;
        }

        throw new IOException("http response error "+response.code()+" "+response.message());
    }

    /**
     * GET 请求
     * @param url
     * @param paramsMap
     * @param okHttpClient
     * @return
     * @throws java.io.IOException
     */
    public static String requestGet(String url, Map<String, String> paramsMap,OkHttpClient okHttpClient) throws IOException {
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
            pos++;
        }

        String requestUrl;
        if(!paramsMap.isEmpty()){
            requestUrl = String.format("%s?%s", url, tempParams.toString());
        }
        else{
            requestUrl = url;
        }
        long span = System.currentTimeMillis();
        Request request = addHeaders().url(requestUrl).build();

        final Call call = okHttpClient.newCall(request);
        final Response response = call.execute();
        String ret = "";
        if (response.isSuccessful()) {
            ret = response.body().string();
        }
        logger.info("url:{},response:{}",url, ret);
        logRequest(url, tempParams.toString(), ret, response.code(), span / 1000, System.currentTimeMillis() - span);
        if (response.isSuccessful()) {
            return ret;
        }
        throw new IOException("http response error " + response.code() + " " + response.message());
    }


    /**
     * GET 请求
     * @param url
     * @param paramsMap
     * @return
     * @throws java.io.IOException
     */
    public static String requestGetByProxy(String url, Map<String, String> paramsMap) throws IOException {
        ConnectionPool connectionPool = new ConnectionPool(100,30000, TimeUnit.MILLISECONDS);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .writeTimeout(30000, TimeUnit.MILLISECONDS)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.17.108", 8118)))
                .connectionPool(connectionPool).build();
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
            pos++;
        }

        String requestUrl;
        if(!paramsMap.isEmpty()){
            requestUrl = String.format("%s?%s", url, tempParams.toString());
        }
        else{
            requestUrl = url;
        }
        long span = System.currentTimeMillis();
        Request request = addHeaders().url(requestUrl).build();

        final Call call = okHttpClient.newCall(request);
        final Response response = call.execute();
        String ret = "";
        if (response.isSuccessful()) {
            ret = response.body().string();
        }
        logger.info("url:{},response:{}",url, ret);
        logRequest(url, tempParams.toString(), ret, response.code(), span / 1000, System.currentTimeMillis() - span);
        if (response.isSuccessful()) {
            return ret;
        }
        throw new IOException("http response error " + response.code() + " " + response.message());
    }

    /**
     * GET 请求
     * @param url
     * @param paramsMap
     * @param okHttpClient
     * @return
     * @throws java.io.IOException
     */
    public static String doJSONGet(String url, Map<String, String> paramsMap,OkHttpClient okHttpClient) throws IOException {
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
            pos++;
        }

        String requestUrl;
        if(!paramsMap.isEmpty()){
            requestUrl = String.format("%s?%s", url, tempParams.toString());
        }
        else{
            requestUrl = url;
        }
        long span = System.currentTimeMillis();
        Request request = addHeaders2().url(requestUrl).build();
        final Call call = okHttpClient.newCall(request);
        final Response response = call.execute();
        String ret = "";
        if (response.isSuccessful()) {
            ret = response.body().string();
        }
        logger.info("url:{},response:{}",url, ret);
        logRequest(url, tempParams.toString(), ret, response.code(), span / 1000, System.currentTimeMillis() - span);
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
     * @param okHttpClient
     * @return
     * @throws java.io.IOException
     */
    public static String requestPost(String requestUrl, String bodyContent,MediaType mediaType,OkHttpClient okHttpClient) throws IOException {
        RequestBody body = RequestBody.create(mediaType, bodyContent);

        long span=System.currentTimeMillis();
        final Request request = addHeaders().url(requestUrl).post(body).build();
        final Call call = okHttpClient.newCall(request);
        Response response = call.execute();

        String ret="";
        if (response.isSuccessful()) {
            ret =response.body().string();
        }
        logRequest(requestUrl, bodyContent.toString(), ret, response.code(), span / 1000, System.currentTimeMillis() - span);

        if (response.isSuccessful()) {
            return ret;
        }

        throw new IOException("http response error "+response.code()+" "+response.message());
    }

    private static void logRequest(String url,String req,String resp,int respCode,long reqTime,long cost){
        logger.info("HTTP LOG--->url:{},req:{},resp:{},respCode:{},reqTime:{},cost:{}", url,req,resp,respCode,(int)reqTime,(int)cost);
    }

    /**
     * form post 请求 上传图片参数默认为image
     * @param requestUrl
     * @param paramsMap
     * @param okHttpClient
     * @return
     * @throws java.io.IOException
     */
    public static String requestUploadImage(String requestUrl, Map<String, String> paramsMap,byte[] image,OkHttpClient okHttpClient) throws IOException {

        return requestUploadImage(requestUrl,paramsMap,image,okHttpClient,"image");
    }


    /**
     * form post 请求
     * @param requestUrl
     * @param paramsMap
     * @param okHttpClient
     * @return
     * @throws java.io.IOException
     */
    public static String requestUploadImage(String requestUrl, Map<String, String> paramsMap,byte[] image,OkHttpClient okHttpClient,String fileParamName) throws IOException {

        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), image);

        builder.addFormDataPart(fileParamName, UUID.randomUUID()+"", fileBody);

        Map<String, String> logParamsMap  = new HashMap<String, String>();

        for (String key : paramsMap.keySet()) {
            builder.addFormDataPart(key, paramsMap.get(key));
            if(!"delta".equals(key)){ //某些字段太长 不记录ej
                logParamsMap.put(key,paramsMap.get(key));
            }
        }

        RequestBody requestBody = builder.build();
        long span=System.currentTimeMillis();

        final Request request = addHeaders().url(requestUrl).post(requestBody).build();
        final Call call = okHttpClient.newCall(request);

        Response response = call.execute();
        String ret="";
        if (response.isSuccessful()) {
            ret= response.body().string();
        }

        logRequest(requestUrl,logParamsMap.toString(),ret,response.code(),span/1000,System.currentTimeMillis()-span );
        if(response.isSuccessful()) {
            return ret;
        }
        if(response.code()>=400&&response.code()<500){
            logger.info("http request 400 error:{}",response.body().source().readUtf8());
        }

        throw new IOException("http response error "+response.code()+" "+response.message());
    }

    /**
     * form post 请求
     * @param requestUrl
     * @param paramsMap
     * @param okHttpClient
     * @param fileName
     * @return
     * @throws java.io.IOException
     */
    public static String requestUploadImage(String requestUrl, Map<String, String> paramsMap,byte[] image,OkHttpClient okHttpClient,String fileParamName,String fileName) throws IOException {

        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), image);

        builder.addFormDataPart(fileParamName, fileName, fileBody);

        Map<String, String> logParamsMap  = new HashMap<String, String>();

        for (String key : paramsMap.keySet()) {
            builder.addFormDataPart(key, paramsMap.get(key));
            if(!"delta".equals(key)){ //某些字段太长 不记录ej
                logParamsMap.put(key,paramsMap.get(key));
            }
        }

        RequestBody requestBody = builder.build();
        long span=System.currentTimeMillis();

        final Request request = addHeaders().url(requestUrl).post(requestBody).build();
        final Call call = okHttpClient.newCall(request);

        Response response = call.execute();
        String ret="";
        if (response.isSuccessful()) {
            ret= response.body().string();
        }

        logRequest(requestUrl,logParamsMap.toString(),ret,response.code(),span/1000,System.currentTimeMillis()-span );
        if(response.isSuccessful()) {
            return ret;
        }
        if(response.code()>=400&&response.code()<500){
            logger.info("http request 400 error:{}",response.body().source().readUtf8());
        }

        throw new IOException("http response error "+response.code()+" "+response.message());
    }

    public static String requestUploadFile(String requestUrl, Map<String, String> paramsMap,byte[] fileBytes,OkHttpClient okHttpClient,String fileParamName,String fileName,MediaType mediaType) throws IOException {

        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);

        RequestBody fileBody = RequestBody.create(mediaType, fileBytes);

        builder.addFormDataPart(fileParamName, fileName, fileBody);

        Map<String, String> logParamsMap  = new HashMap<String, String>();

        for (String key : paramsMap.keySet()) {
            builder.addFormDataPart(key, paramsMap.get(key));
            if(!"delta".equals(key)){ //某些字段太长 不记录ej
                logParamsMap.put(key,paramsMap.get(key));
            }
        }

        RequestBody requestBody = builder.build();
        long span=System.currentTimeMillis();

        final Request request = addHeaders().url(requestUrl).post(requestBody).build();
        final Call call = okHttpClient.newCall(request);

        Response response = call.execute();
        String ret="";
        if (response.isSuccessful()) {
            ret= response.body().string();
        }

        logRequest(requestUrl,logParamsMap.toString(),ret,response.code(),span/1000,System.currentTimeMillis()-span );
        if(response.isSuccessful()) {
            return ret;
        }
        if(response.code()>=400&&response.code()<500){
            logger.info("http request 400 error:{}",response.body().source().readUtf8());
        }

        throw new IOException("http response error "+response.code()+" "+response.message());
    }
}

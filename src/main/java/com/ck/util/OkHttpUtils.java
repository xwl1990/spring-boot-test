
package com.ck.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Description: 
 *
 * @author: xieweili
 * @since: 2016年12月30日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class OkHttpUtils {

    private final static Logger LOG = LoggerFactory.getLogger(OkHttpUtils.class);

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    private static final int MAP_TYPE = 1;

    private static final int TEXT_TYPE = 2;

    private static OkHttpClient getHttpClient(int timeout) {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(timeout, TimeUnit.SECONDS);
        httpClient.setReadTimeout(timeout, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(timeout, TimeUnit.SECONDS);

        return httpClient;
    }

    /**
     * 初始http get请求参数，参数是以字母从小到大排列
     * @param url
     * @param paramMap
     */
    private static Request buildGetUrlEndConnetorParam(String url, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }

        HttpUrl queryUrl = HttpUrl.parse(url);
        if (!paramMap.isEmpty()) {
            TreeMap<String, Object> treeMap = new TreeMap<String, Object>(paramMap);
            String key = treeMap.firstKey();
            Object value = treeMap.get(key);
            queryUrl = getQueryHttpUrl(key, value, queryUrl);
            while ((key = treeMap.higherKey(key)) != null) {
                value = treeMap.get(key);
                queryUrl = getQueryHttpUrl(key, value, queryUrl);
            }
        }

        return new Request.Builder().get().url(queryUrl).build();
    }

    /**
     * 根据key, value组装GET请求URL地扯
     * @param key
     * @param value
     * @param httpUrl
     * @return
     */
    private static HttpUrl getQueryHttpUrl(String key, Object value, HttpUrl httpUrl) {
        if (value != null) {
            if (value instanceof String) {
                httpUrl = httpUrl.newBuilder().addEncodedQueryParameter(key, (String) value).build();
            } else {
                httpUrl = httpUrl.newBuilder().addEncodedQueryParameter(key, String.valueOf(value)).build();
            }
        }

        return httpUrl;
    }

    /**
     * 初始http get请求参数
     * @param url
     * @param paramMap
     */
    private static Request buildPostUrlEndConnetorParam(String url, Map<String, Object> paramMap) {
        return buildUrlEndConnetorParam(url, paramMap, "POST");
    }

    /**
     * 初始http请求参数
     * @param url
     * @param paramMap
     */
    private static Request buildUrlEndConnetorParam(String url, Map<String, Object> paramMap, String method) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            Object value = entry.getValue();
            //不能将null数据加入到builder中，否则会报空指针异常
            if (value != null) {
                if (value instanceof String) {
                    builder.add(entry.getKey(), (String) entry.getValue());
                } else {
                    builder.add(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().method(method, requestBody).url(url).build();
    }

    /**
     * 初始http post请求参数
     * @param url
     * @param jsonString
     * @return
     */
    private static Request buildJsonParam(String url, String jsonString) {
        RequestBody body = RequestBody.create(JSON, jsonString);
        return new Request.Builder().url(url).post(body).build();
    }

    /**
     * 初始http post请求参数
     * @param url
     * @param paramMap
     * @return
     */
    private static Request buildJsonParam(String url, Map<String, Object> paramMap) {
        String jsonString = MapperUtils.toJson(paramMap);
        return buildJsonParam(url, jsonString);
    }

    /**
     * 初始http put请求参数
     * @param url
     * @param jsonString
     * @return
     */
    private static Request buildPutJsonParam(String url, String jsonString) {
        RequestBody body = RequestBody.create(JSON, jsonString);
        return new Request.Builder().method("PUT", body).url(url).build();
    }

    /**
     * 初始http put请求参数
     * @param url
     * @param paramMap
     * @return
     */
    private static Request buildPutJsonParam(String url, Map<String, Object> paramMap) {
        String jsonString = MapperUtils.toJson(paramMap);
        return buildPutJsonParam(url, jsonString);
    }

    @SuppressWarnings("unchecked")
    private static Object newHttpClient(Request request, int type, int timeout) throws Exception {
        Response response = null;
        try {
            OkHttpClient httpClient = getHttpClient(timeout);
            response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String respTxt = response.body().string();
                if (MAP_TYPE == type) {
                    Map<String, Object> result = null;
                    result = MapperUtils.map(respTxt, Map.class);
                    return result;
                }

                return respTxt;
            } else {
                LOG.error("http has response error! reposne code is " + response.code());
                return response;
            }
        } catch (IllegalStateException e) {
            LOG.error("http has illegal state exception. " + e.getMessage(), e);
            return response;
        } catch (InterruptedIOException e) {
            LOG.error("http time out, " + e.getMessage(), e);
            throw e;
        } finally {
            if (response != null) {
                try {
                    response.body().close();
                } catch (IOException e) {
                    LOG.error("http close response throw Exception", e);
                }
            }
        }

    }

    /**
     * http get方式请求，并返回String响应报文
    * @param url
    * @param paramMap
    * @param timeout 单位：秒
    * @return
    * @throws Exception
    */
    public static String httpClientGetReturnAsString(String url, Map<String, Object> paramMap, int timeout) throws Exception {
        Request request = buildGetUrlEndConnetorParam(url, paramMap);
        Object object = newHttpClient(request, TEXT_TYPE, timeout);
        return object != null ? (String) object : null;
    }

    /**
     * http get方式请求，并返回Map响应报文
     * @param url
     * @param paramMap
     * @param timeout
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> httpClientGetReturnAsMap(String url, Map<String, Object> paramMap, int timeout) throws Exception {
        Request request = buildGetUrlEndConnetorParam(url, paramMap);
        Object object = newHttpClient(request, MAP_TYPE, timeout);
        return object != null ? (Map<String, Object>) object : null;
    }

    /**
     * 同步的Post请求 
     * @param url
     * @param jsonString
     * @param timeout
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> httpClientJsonPostReturnAsMap(String url, String jsonString, int timeout) throws Exception {
        Request request = buildJsonParam(url, jsonString);

        Object object = newHttpClient(request, MAP_TYPE, timeout);
        return object != null ? (Map<String, Object>) object : null;
    }

    /**
     * http post方式请求，并返回Map响应报文
     * @param url
     * @param jsonString
     * @param timeout 单位：秒
     * @return
     */
    public static String httpClientJsonPostReturnAsString(String url, String jsonString, int timeout) throws Exception {
        Request request = buildJsonParam(url, jsonString);
        return (String) newHttpClient(request, TEXT_TYPE, timeout);
    }

    /**
     * http post方式请求，并返回Map响应报文
     * @param url
     * @param paramMap
     * @param timeout 单位：秒
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> httpClientJsonPostReturnAsMap(String url, Map<String, Object> paramMap, int timeout) throws Exception {
        Request request = buildJsonParam(url, paramMap);
        Object object = newHttpClient(request, MAP_TYPE, timeout);
        return object != null ? (Map<String, Object>) object : null;
    }

    /**
     * http post方式请求，并返回Map响应报文
     * @param url
     * @param paramMap
     * @param timeout 单位：秒
     * @return
     */
    public static String httpClientJsonPostReturnAsString(String url, Map<String, Object> paramMap, int timeout) throws Exception {
        Request request = buildJsonParam(url, paramMap);
        return (String) newHttpClient(request, TEXT_TYPE, timeout);
    }

    /**
     * http put方式请求，并返回Map响应报文
     * @param url
     * @param jsonString
     * @param timeout 单位：秒
     * @return
     */
    public static String httpClientJsonPutReturnAsString(String url, String jsonString, int timeout) throws Exception {
        Request request = buildPutJsonParam(url, jsonString);
        return (String) newHttpClient(request, TEXT_TYPE, timeout);
    }

    /**
     * http put方式请求，并返回String响应报文
     * @param url
     * @param paramMap
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> httpClientJsonPutReturnAsString(String url, Map<String, Object> paramMap, int timeout) throws Exception {
        Request request = buildPutJsonParam(url, paramMap);
        Object object = newHttpClient(request, MAP_TYPE, timeout);
        return object != null ? (Map<String, Object>) object : null;
    }

    /**
     * http post方式请求，并返回String响应报文
     * @param url
     * @param paramMap
     * @param timeout 单位：秒
     * @return
     */
    public static String httpClientPostReturnJson(String url, Map<String, Object> paramMap, int timeout) throws Exception {
        Request request = buildPostUrlEndConnetorParam(url, paramMap);
        return (String) newHttpClient(request, TEXT_TYPE, timeout);

    }

    /**
     * http post方式提交form请求，并返回map响应报文
     * @param url
     * @param paramMap
     * @param timeout 单位：秒
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> httpClientPostFormReturnMap(String url, Map<String, Object> paramMap, int timeout) throws Exception {
        Request request = buildPostUrlEndConnetorParam(url, paramMap);
        Object object = newHttpClient(request, MAP_TYPE, timeout);
        return object != null ? (Map<String, Object>) object : null;

    }

    /**
     * xml 请求
     * 
     * @param url
     * @param obj
     * @return
     * @throws Exception
     */
    public static String clientPostXmlRetTxt(String url, String xml, int timeout) throws Exception {
        if (StringUtils.isEmpty(xml)) {
            return null;
        }

        Request request = buildPostXmlParam(url, xml);
        return (String) newHttpClient(request, TEXT_TYPE, timeout);
    }

    /**
     * 初始http post请求xml参数
     * @param url
     * @param xmlString
     * @return
     */
    private static Request buildPostXmlParam(String url, String xmlString) {
        if (StringUtils.isEmpty(xmlString)) {
            return new Request.Builder().url(url).build();
        }

        RequestBody body = RequestBody.create(XML, xmlString);
        return new Request.Builder().method("POST", body).url(url).build();
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        String json =
                "{\"mccGroup\":\"05\",\"mchtNo\":\"001310156990001\",\"mchtSource\":\"3\",\"mchtKind\":\"B2\",\"settleCycle\":\"1\",\"cardType\":\"\",\"transAmt\":\"300\",\"cardNo\":\"\",\"auditStatus\":\"0\",\"transType\":\"31\",\"orderNo\":\"99436508735598326459\",\"brhNo\":\"002\",\"paymentMethod\":\"4\",\"boxSn\":\"\"}";
        Map<String, Object> map = MapperUtils.map(json, Map.class);
        try {
            Map<String, Object> ret = httpClientPostFormReturnMap("http://1172.30.0.129:18080/fspf-rcmms/service/riskControlCheck.htm", map, 10);
            System.out.print(ret);
        } catch (Exception e) {

        }
    }
}

package com.sallai.music.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: $
 * @author: sallai
 * @time: 2022年1月3日 0003 下午 07:02:38 秒
 */
@Slf4j
@Component
public class OkHttpUtil {

    /**
     * okhttp get method
     * @return
     * @throws IOException
     */
    public Response sendGetRequest(String url,Map<String,String> headerMap) throws IOException {
        log.info("url->",url);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        headerMap.entrySet().removeIf(entry -> entry.getValue() == null);
        Headers headers = Headers.of(headerMap);
        Request request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .build();
        final Call call = okHttpClient.newCall(request);
        return call.execute();
    }

    /**
     * 获取请求头内容
     * @param httpRequest
     * @return
     */
    public Map<String,String> getRequestHeaderMap(HttpServletRequest httpRequest,Set<String> contain) {
        Map<String,String> reqHeaderMap = new HashMap<>();
        if(null == contain) {
            contain = new HashSet<>();
        }
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            boolean flag = false;
            for (String s : contain) {
                if(!StringUtils.containsIgnoreCase(s,key)){
                    flag = true;
                    break;
                }
            }
            if(flag) {
                continue;
            }
            reqHeaderMap.put(key,httpRequest.getHeader(key));
        }
        return reqHeaderMap;
    }

    /**
     * okhttp post method
     * @return
     * @throws IOException
     */
    public ResponseBody sendPost() throws IOException {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
//        log.debug( "run: " + response.body().string());
        return body;
    }

    public String sendGetRequest(String url) throws IOException {
        log.info("get url->"+url);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Request request = null;
        request = new Request.Builder()
                    .url(url)
                    .build();
        final Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
        String res = body.string();
        return res;
    }

}



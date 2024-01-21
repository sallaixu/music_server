package com.sallai.music.utils;


import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import static com.sallai.music.utils.AppConstant.API_TOEKN;

@Slf4j
public class Http {


    private final String TAG = this.getClass().getSimpleName();

    public static String sendHttp(String url){
        OkHttpClient client = getClient();
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .header("custom",API_TOEKN)
                .build();

        try {
            assert client != null;
            try (Response response= client.newCall(request).execute()) {
                //解析成小讯格式
                return Objects.requireNonNull(response.body()).string();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("okHttp","okhttp->error");
        return "ERROR";
    }


    public static String getHttpCookie(String url){

        OkHttpClient client = getClient();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .header("custom",API_TOEKN)
                .build();

        try {
            assert client != null;
            try (Response response= client.newCall(request).execute()) {
                Headers  headers=response.headers();
                //获取cookie
                List<String> cookies = headers.values("Set-Cookie");
                return cookies.toString();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("okHttp","okhttp->error");
        return "ERROR";

    }

    public static String sendPostHttp(String url, String data){
        RequestBody body = RequestBody.create(data,MediaType.parse("application/json"));
        OkHttpClient client = getClient();

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .header("custom",API_TOEKN)
                .post(body)
                .build();

        try {
            assert client != null;
            try (Response response= client.newCall(request).execute()) {
                //解析成小讯格式
                return Objects.requireNonNull(response.body()).string();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("okHttp","okhttp->error");
        return "ERROR";

    }


    public static boolean downloadSmallFile(final String uri, final String filePath) {
        String httpCookie = getHttpCookie(uri);
        httpCookie = httpCookie.replaceAll("\\[","").replaceAll("]", "");
        System.out.println(httpCookie);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(uri.toString())
                .header("custom",API_TOEKN)
                .addHeader("Cookie",httpCookie)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                System.out.println("响应失败");
                return false;
            }

            ResponseBody body = response.body();
            assert body != null;
            long contentLength = body.contentLength();
            System.out.println("响应长度"+contentLength);
            BufferedSource source = body.source();
            File file = new File(filePath);
            BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeAll(source);
            sink.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static OkHttpClient getClient() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        // 获取默认的 SSLContext 实例
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;

    }

}

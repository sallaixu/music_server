package com.sallai.music.server.music;

import com.sallai.music.module.pojo.DownLoadInfoPojo;
import com.sallai.music.utils.OkHttpUtil;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

/**
 * @ClassName MusicService
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 23:29 2024/3/11
 * @Version 1.0
 **/
@Service
public class MusicService {
    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    OkHttpUtil okHttpUtil;

    private void handlerBin(ResponseBody resp) {
        InputStream inputStream = resp.byteStream();
        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            // 在http响应中输出流
            byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = inputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, nRead);
                outputStream.flush();
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            resp.close();

        }

    }

    /**
     * 输入音乐url，进行下载代理
     * @param url 音乐url
     */
    public void downLoad(String  url) {
        Response response = null;
        try {
            response = okHttpUtil.sendGetRequest(url, new HashMap<>(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response != null;
        httpServletResponse.setContentType("audio/mpeg");
        handlerBin(Objects.requireNonNull(response.body()));
    }
}

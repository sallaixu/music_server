package com.sallai.music.server.music.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sallai.music.bean.MusicInfoBean;
import com.sallai.music.bean.MusicListVo;
import com.sallai.music.server.music.AbstractMusic;
import com.sallai.music.utils.Http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;


/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/9/6
 */
public class HifiniMusic extends AbstractMusic {
    private final String searchUrl = "http://music.918123.xyz/vk_auth.php?q=";
    private final String baseHost = "http://music.918123.xyz/";

    @Override
    public MusicListVo searchMusic(String keyword, int size) {
        String data = Http.sendHttp(searchUrl + keyword);
        JSONObject dataJson = JSON.parseObject(data);
        JSONObject audios = dataJson.getJSONObject("audios");
        JSONArray musicArray = audios.getJSONArray("");
        int count = Math.min(size, (musicArray.size() - 1));
        MusicListVo musicVo = MusicListVo.builder().totalTime(10000).count(count).build();
        for (int i = 0; i < count; i++) {
            JSONObject jsonObject = musicArray.getJSONObject(i);
            String url = jsonObject.getString("url");
            url = StringUtils.contains(url,"http") ? url : baseHost + url;
            MusicInfoBean info = MusicInfoBean.builder().id(jsonObject.getString("id"))
                    .artist(jsonObject.getString("tit_art"))
                    .duration(jsonObject.getInteger("duration"))
                    .url(url).build();
            musicVo.getMusicinfo().add(info);
        }
        return musicVo;
    }

    private String getUrlEncode(String keyword) {
        try {
            String url = URLEncoder.encode(keyword, "UTF-8");
            url = url.replaceAll("\\_", "%5f");
            url = url.replaceAll("\\-", "%2d");
            url = url.replaceAll("\\.", "%2e");
            url = url.replaceAll("\\~", "%7e");
            url = url.replaceAll("\\!", "%21");
            url = url.replaceAll("\\*", "%2a");
            url = url.replaceAll("\\(", "%28");
            url = url.replaceAll("\\)", "%29");
            return url;
        //encodedS = encodedS.replace('+', '%20');
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }

    public static void main(String[] args) {
       String res = new HifiniMusic().getUrlEncode("蔡琴");
       System.out.println(res);
        // MusicListVo musicVo = new SliderKzMusic().searchMusic("泪蛋蛋掉在酒杯杯里", 20);
        // for (MusicInfoBean musicInfoBean : musicVo.getMusicinfo()) {
        //     System.out.println(musicInfoBean);
        // }
    }
}

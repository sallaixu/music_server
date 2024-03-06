package com.sallai.music.server.music.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sallai.music.bean.MusicInfoBean;
import com.sallai.music.bean.MusicListVo;
import com.sallai.music.server.music.AbstractMusic;
import com.sallai.music.utils.Http;


import org.apache.commons.lang3.StringUtils;


/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/9/6
 * @author sallai
 */
public class SliderKzMusic extends AbstractMusic {
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
            String title = jsonObject.getString("tit_art");
            String artist = "";
            if(title != null 
               && title.contains("-")) {
               String[] splitStr = title.split("-");
               if(splitStr.length == 2) {
                   artist = splitStr[0].trim();
                   title = splitStr[1].trim();
               } 
           }
            MusicInfoBean info = MusicInfoBean.builder().id(jsonObject.getString("id"))
                    .artist(artist)
                    .title(title)
                    .duration(jsonObject.getInteger("duration"))
                    .url(url).build();
            musicVo.getMusicinfo().add(info);
        }
        return musicVo;
    }

    public static void main(String[] args) {
        MusicListVo musicVo = new SliderKzMusic().searchMusic("泪蛋蛋掉在酒杯杯里", 20);
        for (MusicInfoBean musicInfoBean : musicVo.getMusicinfo()) {
            System.out.println(musicInfoBean);
        }
    }
}

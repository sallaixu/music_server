package com.sallai.music.server.music.impl;

import static com.sallai.music.module._enum.MusicServiceEnum.NET_EASY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sallai.music.bean.MusicInfoBean;
import com.sallai.music.bean.MusicListVo;
import com.sallai.music.server.music.AbstractMusic;
import com.sallai.music.utils.AppConstant;
import com.sallai.music.utils.Http;


/**
 * author:sallai@aliyun.com
 */
public class NetEasyMusic extends AbstractMusic {

    //http://music.163.com/song/media/outer/url?id=1486631924
    private static final String SEARCH_URL = AppConstant.R1_CUSTOM_DOMAIN + "/cloudsearch?offset=0";
    private static final String PLAY_URL = "http://music.163.com/song/media/outer/url?id=";
    private static final String HOT_SONGS = AppConstant.R1_CUSTOM_DOMAIN + "/playlist/detail?id=8246775932"; //实时热榜
    private static final String USER_SONGS = "http://music.163.com/api/user/playlist/?offset=0&limit=1001&uid="; // 1593331566获取用户收藏及歌单信息//2412967068 获取歌单详情
    private static final String RANDOM_MUSIC = "https://api.uomg.com/api/rand.music?sort=%E7%83%AD%E6%AD%8C%E6%A6%9C&format=json";
    private static final String GET_PLAY_URL = AppConstant.R1_CUSTOM_DOMAIN + "/song/url/v1?level=standard&id=";
    private static final String TAG = NetEasyMusic.class.getSimpleName();
    /*offset：偏移量（翻页），offset需要是limit的倍数
            type：搜索的类型
            type=1           单曲
            type=10         专辑
            type=100        歌手
            type=1000      歌单
            type=1002      用户
            type=1004      MV
            type=1006      歌词
            type=1009      主播电台*/

    /**
     * 搜索
     *
     * @param keyword 搜索关键词
     * @param size 结果数量
     * @return
     */
    @Override
    public MusicListVo searchMusic(String keyword, int size) {
        String url = "";
        JSONArray jsonArray = null;
        if (Strings.isEmpty(keyword)) {
            keyword = "热歌榜";
            url = HOT_SONGS;
        }else{
            url = SEARCH_URL + "&limit=" + size + "&type=1&keywords=" + keyword;
        }
        String body = Http.sendPostHttp(url, "");

        if(keyword.equals("热歌榜")) {
            jsonArray = JSON.parseObject(body).getJSONObject("playlist").getJSONArray("tracks");
        }else{
            jsonArray = JSON.parseObject(body).getJSONObject("result").getJSONArray("songs");
        }
        return parseJson(jsonArray);
    }

    /**
     * 解析json数据
     *
     * @param jsonArray
     * @return 音乐vo
     */
    private static MusicListVo parseJson(JSONArray jsonArray) {

        List<MusicInfoBean> musicList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            MusicInfoBean m = new MusicInfoBean();
            JSONObject res = jsonArray.getJSONObject(i);
            m.setArtist(res.getJSONArray("ar").getJSONObject(0).getString("name"));
            m.setAlbum(res.getJSONObject("al").getString("name"));
            m.setTitle(res.getString("name"));
            m.setDuration(res.getInteger("dt"));
            m.setId(res.getString("id"));
            m.setSourceType(NET_EASY);
            musicList.add(m);
            
        }
        MusicListVo musicResult = new MusicListVo();
        musicResult.setDataSourceName(NET_EASY.getName());
        musicResult.setErrorCode(0);
        musicResult.setMusicinfo(musicList);
        musicResult.setTotalTime(900);
        setUrl(musicResult);
        musicResult.setCount(musicList.size());
        return musicResult;
    }


    /**
     * 获取搜索音乐id的歌曲地址
     * @param musicResult
     */
    private static void setUrl(MusicListVo musicResult) {
        Map<String, MusicInfoBean> musicMap = new HashMap<>(6);
        for (MusicInfoBean musicInfoBean : musicResult.getMusicinfo()) {
            musicMap.put(musicInfoBean.getId(), musicInfoBean);
        }
        String ids = StringUtils.join(musicMap.keySet(), ',');

        String res = Http.sendHttp(GET_PLAY_URL + ids);
        JSONObject jsonObject = JSON.parseObject(res);
        JSONArray songs = jsonObject.getJSONArray("data");
        for (int i = 0; i < songs.size(); i++) {
            JSONObject obj = songs.getJSONObject(i);
            String id = obj.getString("id");
            MusicInfoBean musicInfoBean = musicMap.get(id);
            if (null != musicInfoBean) {
                musicInfoBean.setUrl(obj.getString("url"));
                musicInfoBean.setDuration(obj.getInteger("time"));
                if(musicInfoBean.getDuration() < 40 * 1000) {
                    musicResult.getMusicinfo().remove(musicInfoBean);
                }
            }
        }
    }

    public static void main(String[] args) {
        NetEasyMusic wyyMusic = new NetEasyMusic();
        MusicListVo vo = wyyMusic.searchMusic("蔡琴", 20);
        System.out.println(vo);
    }
}






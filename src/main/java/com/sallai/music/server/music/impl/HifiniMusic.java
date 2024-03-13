package com.sallai.music.server.music.impl;

import static com.sallai.music.module._enum.MusicServiceEnum.HIFI_NI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import com.sallai.music.bean.MusicInfoBean;
import com.sallai.music.bean.MusicListVo;
import com.sallai.music.server.music.AbstractMusic;
import com.sallai.music.utils.Http;

import lombok.extern.slf4j.Slf4j;


/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/9/6
 */
@Slf4j
public class HifiniMusic extends AbstractMusic {
    private final String searchUrl = "https://www.hifini.com/search-";
    private final String baseHost = "https://www.hifini.com/";
    private final String suffix = "-1-1.htm";

    @Override
    public MusicListVo searchMusic(String keyword, int size) {
        MusicListVo musicVo = MusicListVo.builder().totalTime(10000).build();
        String html = Http.sendHttp(searchUrl + keyword + suffix);
        Document doc = Jsoup.parse(html);
        Elements liList = doc.select("li.media.thread.tap");
        liList.forEach(ele->{
             Element aTag = ele.selectXpath("//div/div/a").first();
             String txt = aTag.text();
             if(!txt.contains("《")) return;
             String title = subString("《", "》", txt);
             String arist = subString("","《",txt);
             String id = aTag.attr("href");
             log.info(arist + title + id);
             MusicInfoBean vo = MusicInfoBean.builder().id(id)
                    .artist(arist)
                    .sourceType(HIFI_NI)
                    .title(title).build();
            musicVo.getMusicinfo().add(vo);
        });
        musicVo.setCount(musicVo.getMusicinfo().size());
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

    @Override
    public MusicInfoBean getMusicDetailInfo(String musicId) {
        String html = Http.sendHttp(baseHost + musicId);
        String title = subString("title: '","'", html);
        String artist = subString("author:'","'", html);
        String url = subString("url: '","'", html);
        String pic = subString("pic: '","'", html);
        MusicInfoBean vo = MusicInfoBean.builder().id(musicId).title(title)
        .artist(artist).url(baseHost + url).imgUrl(pic).build();
        String lyric = parseLrc(html);
        vo.setLyric(lyric);
        return vo;
    }


    private String parseLrc(String data) {
        Document html = Jsoup.parse(data);
        Element div = html.selectXpath("//*[@id='body']/div/div/div[1]/div[1]/div/div[2]").first();
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (Element sub : div.children()) {
            if(sub.tagName().equalsIgnoreCase("h5")){
                flag = !flag;
            }
            if(!flag && result.length() > 0) {
                break;
            } else{
                if(StringUtils.hasText(sub.text().trim())) {
                    result.append("[10:00.929]").append(sub.text().trim()).append("\n");
                }
            }
        }
        return result.toString();

    }

    public static void main(String[] args) {
    
       new HifiniMusic().searchMusic("蔡琴",10);
        // MusicListVo musicVo = new SliderKzMusic().searchMusic("泪蛋蛋掉在酒杯杯里", 20);
        // for (MusicInfoBean musicInfoBean : musicVo.getMusicinfo()) {
        //     System.out.println(musicInfoBean);
        // }
    }
}

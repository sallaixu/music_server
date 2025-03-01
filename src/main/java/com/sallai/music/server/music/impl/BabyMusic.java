package com.sallai.music.server.music.impl;

import static com.sallai.music.module._enum.MusicServiceEnum.BABY_MUSIC;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sallai.music.bean.MusicInfoBean;
import com.sallai.music.bean.MusicListVo;
import com.sallai.music.module._enum.MusicServiceEnum;
import com.sallai.music.server.music.AbstractMusic;
import com.sallai.music.utils.CharEncodeUtil;
import com.sallai.music.utils.Http;
import com.sallai.music.utils.MyThreadPool;

import lombok.extern.slf4j.Slf4j;


/**
 * Description: [对类的简单描述]
 * <p>
 * Author: 歌曲宝网址解析
 * Date: 2023/8/23
 * url: https://www.gequbao.com
 */
@Slf4j
public class BabyMusic extends AbstractMusic {
    public static final String searchUrl = "https://www.gequbao.com/s/";
    public static final String getPlayUrl = "https://www.gequbao.com/music/";
    public static final String getPlayMusicUrl = "https://www.gequbao.com/api/play-url";
    public static final ExecutorService pool = MyThreadPool.getThreadPool();
    private static final String TAG = "MusicBaby";

    @Override
    public MusicListVo searchMusic(String keyword, int size) {
        String url = searchUrl + keyword;
        String data = Http.sendHttp(url);
        List<MusicInfoBean> musicInfoBeans = parseHtml(data, size);
        return MusicListVo.builder().count(musicInfoBeans.size()).musicinfo(musicInfoBeans)
                .dataSourceName(BABY_MUSIC.getName())
                .totalTime(1000 * 60 * 10).build();
    }

    private List<MusicInfoBean> parseHtml(String html,int size) {
        // CountDownLatch countDownLatch = new CountDownLatch(size);
        Document parse = Jsoup.parse(html);
        Elements elementsByClass = parse.body().getElementsByClass("card mb-1");
        Element container = Objects.requireNonNull(elementsByClass.first()).getElementsByClass("card-text").first();
        assert container != null;
        Elements musicElements = container.children();
        int count = Math.min(size, (musicElements.size() - 2));
        List<MusicInfoBean> musicList = new ArrayList<>();
        for (int i = 1 ; i <= count; i++ ) {
            Element element = musicElements.get(i);
            Element aTag = element.getElementsByTag("a").first();
            assert aTag != null;
            String idStr = aTag.attr("href");
            if(StringUtils.isEmpty(idStr)) {
                continue;
            }
            String id = idStr.substring(idStr.lastIndexOf("/") + 1);
            Element authorDiv = element.getElementsByClass("text-success col-4 col-content").first();
            String author = "无名氏";
            if(null != authorDiv) {
                author = authorDiv.text();
            }
            MusicInfoBean musicBeanVo = MusicInfoBean.builder()
                    .sourceType(MusicServiceEnum.BABY_MUSIC)
                    .url(getPlayMusicUrl+id)
                    .duration(1000 * 60 * 3).title(aTag.text())
                    .artist(author).id(id).build();
        //    getPlayUrl(id,,musicBeanVo,countDownLatch);
           musicList.add(musicBeanVo);
        }
    //    waitRequestOkForTime(countDownLatch, musicList);
        return musicList;
    }

    /**
     * 获取歌曲详细信息
     * @param musicId
     * @return
     */
    @Override
    public MusicInfoBean getMusicDetailInfo(String musicId) {
        String html = Http.sendHttp(getPlayUrl + musicId);
        Document doc = Jsoup.parse(html);
        // String lrcStr = "window.mp3_lrc = `";
        String author = subString("window.mp3_author = '","';",html);
        String lrc = "";
        Element lrcDiv = doc.getElementById("content-lrc");
        if(null != lrcDiv) {
            lrc = lrcDiv.wholeText();
        }
        String convert = subString("window.mp3_cover = '", "';", html);
        String title = subString("window.mp3_title = '", "';", html);
        String play_id = subString("window.play_id = '", "';", html);
        String playUrl = getPlayUrl(play_id);
        MusicInfoBean infoBean = MusicInfoBean.builder().id(musicId).lyric(lrc).imgUrl(convert)
                .artist(author).title(title)
                .url(playUrl).build();
        return infoBean;
    }

    private String getPlayUrl(String playId){
        String data = "id=" + playId;
        String res = Http.sendPostHttpFormData(getPlayMusicUrl,data);
        try {
           JSONObject jb = JSON.parseObject(res);
           return jb.getJSONObject("data").getString("url");
        }catch(Exception e) {
            log.error("获取歌曲播放链接错误");
            return "";
        }
        
    }

//    private void getPlayUrl(final String urlPath,String data, final MusicInfoBean musicInfoBean, final CountDownLatch countDownLatch) {
//        pool.execute(new Runnable() {
//            @Override
//            public void run() {
//                String json = Http.sendPostHttp(getPlayUrl, data);
//                log.info(json)
//             //    String flagStr = "$('#btn-download-mp3').attr('href', '";
//             //    String lrcStr = "lrc: '\"";
//             //    String playUrl = subString(flagStr,"');",html);
//             //    String lrc = CharEncodeUtil.unicodeToUtf8(subString(lrcStr,"\"'",html));
//             //    String convert = subString("cover: '", "',", html);
//             //    musicInfoBean.setImgUrl(convert);
//             //    musicInfoBean.setLyric(lrc);
//             //    musicInfoBean.setUrl(playUrl);
//                countDownLatch.countDown();
//            }
//        });
//    }

    


    public static void main(String[] args) {
        BabyMusic babyMusic = new BabyMusic();
        MusicInfoBean detail = babyMusic.getMusicDetailInfo("20");
        System.out.println(detail);
        // long start = System.currentTimeMillis();
        // MusicListVo vo = babyMusic.searchMusic("周杰伦", 6);
        // System.out.println(vo);
        // System.out.println(System.currentTimeMillis() - start + "ms");
    }
}

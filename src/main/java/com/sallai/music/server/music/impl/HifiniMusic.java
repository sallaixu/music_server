package com.sallai.music.server.music.impl;

import com.sallai.music.bean.MusicInfoBean;
import com.sallai.music.bean.MusicListVo;
import com.sallai.music.server.music.AbstractMusic;
import com.sallai.music.utils.Http;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.sallai.music.module._enum.MusicServiceEnum.HIFI_NI;


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

    private static final String BASE32_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final char PAD_CHAR = '=';
    private static final String key = "95wwwHiFiNicom27";

    @Override
    public MusicListVo searchMusic(String keyword, int size) {
        MusicListVo musicVo = MusicListVo.builder().totalTime(10000).build();
        String html = Http.sendHttp(searchUrl + keyword + suffix);
        Document doc = Jsoup.parse(html);
        Elements liList = doc.select("li.media.thread.tap");
        liList.forEach(ele->{
             Element aTag = ele.selectXpath("//div/div/a").first();
             String txt = aTag.text();
             if(!txt.contains("《")) {
                 return;
             }
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
        //String param = subString("generateParam('","')", html);
        //String genParam = generateParam(param);
        //url += genParam;
        String pic = subString("pic: '","'", html);
        //String str = subString("generateParam('","'",html);
        MusicInfoBean vo = MusicInfoBean.builder().id(musicId).title(title)
        .artist(artist).url(url).imgUrl(pic).build();
        String lyric = parseLrc(html);
        vo.setLyric(lyric);
        return vo;
    }

    private String genKey(String data,String key) {
        char[] outText = new char[data.length()];

        for (int i = 0, j = 0; i < data.length(); i++, j++) {
            if (j == key.length()) j = 0;
            outText[i] = (char) (data.charAt(i) ^ key.charAt(j));
        }
        return base32Encode(new String(outText));
    }

        public static String base32Encode(String str) {
            StringBuilder bits = new StringBuilder();
            StringBuilder base32 = new StringBuilder();

            for (int i = 0; i < str.length(); i++) {
                String bit = Integer.toBinaryString(str.charAt(i));
                while (bit.length() < 8) {
                    bit = "0" + bit;
                }
                bits.append(bit);
            }

            while (bits.length() % 5 != 0) {
                bits.append("0");
            }

            for (int i = 0; i < bits.length(); i += 5) {
                String chunk = bits.substring(i, i + 5);
                int index = Integer.parseInt(chunk, 2);
                base32.append(BASE32_CHARS.charAt(index));
            }

            while (base32.length() % 8 != 0) {
                base32.append("=");
            }

            return base32.toString().replace("=", "HiFiNiYINYUECICHANG");
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

        MusicInfoBean musicDetailInfo = new HifiniMusic().getMusicDetailInfo("thread-916.htm");
        System.out.println(musicDetailInfo);
//
//         MusicListVo musicVo = new HifiniMusic().searchMusic("周杰伦", 20);
//         for (MusicInfoBean musicInfoBean : musicVo.getMusicinfo()) {
//             System.out.println(musicInfoBean);
//         }
//        String data = "SxxHiFiNixxxxHiFiNixxVRuxxHiFiNixxumaHH3Fd2tHYyZDzh80ByHGAnWhdvMuymxD8AxxHiFiNixxL52B6uv1CQIBc05kuYAvEN7KFgHdPoBD085XwwwHiFiNicomwwwHiFiNicom5xf3JDaxNfPy83okP3kLScwCkGiuMcyC4PcynBAg"; // Replace with your input data
//        String result = generateParam(data);
//        System.out.println("Generated parameter: " + result);
    }

    /**
     * Generate a parameter for the Hifini API request.
     * @param data  The data to be encrypted.
     * @return  The encrypted parameter.
     */
    public static String generateParam(String data) {
        String key = "95wwwHiFiNicom27";
        StringBuilder outText = new StringBuilder();

        for (int i = 0, j = 0; i < data.length(); i++, j++) {
            if (j == key.length()) j = 0;
            char xorResult = (char) (data.charAt(i) ^ key.charAt(j));
            outText.append(xorResult);
        }

        // Encode the result using Base32 (custom implementation)
        return base32Encode(outText.toString());
    }
}

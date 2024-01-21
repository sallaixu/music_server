package com.sallai.music.module._enum;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/9/7
 * @author sallai
 */
public enum MusicServiceEnum {
    //网易音乐
    NET_EASY("网易云音乐"),
    //下歌宝音乐
    BABY_MUSIC("下歌宝音乐"),
    //vip音乐
    SLIDER_KZ("vip音乐");
    private String name;
    private MusicServiceEnum () {

    }

    private MusicServiceEnum (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

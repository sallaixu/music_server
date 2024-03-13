package com.sallai.music.server.music;

import com.sallai.music.module._enum.MusicServiceEnum;
import com.sallai.music.server.music.impl.BabyMusic;
import com.sallai.music.server.music.impl.HifiniMusic;
import com.sallai.music.server.music.impl.NetEasyMusic;
import com.sallai.music.server.music.impl.SliderKzMusic;

/**
 * @ClassName MusicInstanceFactory
 * @Description 音乐服务实现工厂类
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 0:11 2024/3/3
 * @Version 1.0
 **/
public class MusicInstanceFactory {

    public static BaseMusicInterface getInstance(String type) {
            MusicServiceEnum musicServiceEnum = MusicServiceEnum.valueOf(type);
            BaseMusicInterface musicInterface = null;
            switch (musicServiceEnum) {
                case BABY_MUSIC:
                    musicInterface = new BabyMusic();break;
                case NET_EASY:
                    musicInterface = new NetEasyMusic();break;
                case SLIDER_KZ:
                    musicInterface = new SliderKzMusic();break;
                case HIFI_NI:
                    musicInterface = new HifiniMusic();break;
                default:
                    musicInterface = new SliderKzMusic();break;
            }
            return musicInterface;
    }

}

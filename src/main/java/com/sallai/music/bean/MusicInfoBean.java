package com.sallai.music.bean;

import com.sallai.music.module._enum.MusicServiceEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 音乐实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicInfoBean {

    private int duration;
    private String imgUrl;
    private String hdImgUrl;
    private String artist;
    private String lyric;
    private String album;
    private String id;
    private String title;
    private String url;
    private MusicServiceEnum sourceType; 
    @Builder.Default
    private int audioType = 1;
    @Builder.Default
    private int play_count = 10000;
    @Builder.Default
    private String domainName = "music";
    @Builder.Default
    private String tags = "流行";
    
}

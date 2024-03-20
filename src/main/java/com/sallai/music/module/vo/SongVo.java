package com.sallai.music.module.vo;

import com.sallai.music.module.entity.SongEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName SongVo
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 1:58 PM 3/16/2024
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongVo {

    private String id;
    @NotEmpty(message = "歌曲名字不能为空")
    private String title;
    @NotEmpty(message = "音源类型不能为空")
    private String sourceType;
    private String artist;
    @NotEmpty(message = "音乐id不能为空")
    private String musicId;
    private String songTableId;
    private String playUrl;

    /**
     * 转换实体
     * @param song
     * @return
     */
    public static SongVo convert(SongEntity song) {
        return SongVo.builder().id(song.getMusicId()).title(song.getName())
                .sourceType(song.getSourceType()).artist(song.getArtist()).musicId(song.getId())
                .songTableId(song.getSongTableId()).playUrl(song.getPlayUrl())
                .build();
    }
}

package com.sallai.music.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName SongEntity
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 1:22 PM 3/16/2024
 * @Version 1.0
 **/
@Data
@TableName("`song`")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongEntity {
    @TableId
    private String id;
    private String name;
    @TableField("source_type")
    private String sourceType;
    private String artist;
    @TableField("music_id")
    private String musicId;
    @TableField("song_table_id")
    private String songTableId;
    @TableField("play_url")
    private String playUrl;
    @TableField("user_id")
    private String userId;

}

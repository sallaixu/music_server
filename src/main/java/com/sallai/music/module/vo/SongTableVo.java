package com.sallai.music.module.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sallai.music.module.entity.SongEntity;
import com.sallai.music.module.entity.SongTableEntity;
import com.sallai.music.module.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName UserVo
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
public class SongTableVo {

    private String id;
    private String name;
    private String img;
    private String desc;
    private String userId;
    private Date createTime;

    /**
     * 转换实体
     * @param songTable
     * @return
     */
    public static SongTableVo convert(SongTableEntity songTable) {
        return SongTableVo.builder().id(songTable.getId()).name(songTable.getName())
                .img(songTable.getImg()).desc(songTable.getDesc()).createTime(songTable.getCreateTime())
                .build();
    }
}

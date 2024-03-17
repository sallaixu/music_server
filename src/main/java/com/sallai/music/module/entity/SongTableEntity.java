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
 * @ClassName SongTableEntity
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
public class SongTableEntity {
    @TableId
    private String id;
    private String name;
    private String img;
    private String desc;
    @TableField("user_id")
    private String userId;
    @TableField("create_time")
    private Date createTime;

}

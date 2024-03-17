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
 * @ClassName UserEntity
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 1:22 PM 3/16/2024
 * @Version 1.0
 **/
@Data
@TableName("`user`")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @TableId
    private String id;
    private String name;
    @TableField("ipAddr")
    private String ipAddr;
    private String avatar;
    @TableField("firstLoginTime")
    private Date firstLoginTime;
    @TableField("lastLoginTime")
    private Date lastLoginTime;
}

package com.sallai.music.module.vo;

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
public class UserVo {

    private String id;
    private String name;

    private String ipAddr;

    private String avatar;

    private Date firstLoginTime;

    private Date lastLoginTime;

    /**
     * 转换实体
     * @param user
     * @return
     */
    public static UserVo convert(UserEntity user) {
        return UserVo.builder().id(user.getId()).name(user.getName())
                .avatar(user.getAvatar()).build();
    }
}

package com.sallai.music.module.pojo;

import lombok.*;

/**
 * @ClassName QrCallBackPojo
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 9:34 PM 3/15/2024
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QrCallBackPojo {
    private String userId;
    private String tempUserId;
    private String nickname;
    private String avatar;
    private String ipAddr;
}

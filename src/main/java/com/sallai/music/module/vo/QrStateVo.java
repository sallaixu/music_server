package com.sallai.music.module.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName QrStateVo
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 9:48 PM 3/15/2024
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrStateVo {
    private Integer errcode;
    private String message;
}

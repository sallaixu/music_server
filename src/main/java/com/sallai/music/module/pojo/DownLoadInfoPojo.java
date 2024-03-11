package com.sallai.music.module.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName DownLoadInfoPojo
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 23:43 2024/3/11
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownLoadInfoPojo {
    /**
     * MP3 下载链接
     */
    private String url;

}

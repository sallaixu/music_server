package com.sallai.music.utils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sallai.music.module.vo.SongVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 12:55 AM 3/17/2024
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResult<T> {
    /**
     * 当前页码
     */
    private long pageNum;
    /**
     * 每页数量
     */
    private long pageSize;
    /**
     * 记录总数
     */
    private long totalSize;
    /**
     * 页码总数
     */
    private long totalPages;
    /**
     * 数据模型
     */
    private List<T> content;

    public static <T> PageResult<T> convert(IPage pageResult,Class<T> tClass) {

        return PageResult.<T>builder().totalPages(pageResult.getPages()).pageNum(pageResult.getCurrent())
                .pageSize(pageResult.getSize()).totalSize(pageResult.getTotal()).build();
    }
}

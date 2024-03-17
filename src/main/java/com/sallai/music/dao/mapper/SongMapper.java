package com.sallai.music.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sallai.music.module.entity.SongEntity;
import com.sallai.music.module.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName SongMapper
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 1:25 PM 3/16/2024
 * @Version 1.0
 **/
public interface SongMapper extends BaseMapper<SongEntity> {
    @Select("select * from song s where s.user_id = #{userId}")
    IPage<SongEntity> queryLikeSongByPage(Page<SongEntity> page, @Param("userId") String userId);
}

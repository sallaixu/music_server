package com.sallai.music.server.music;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sallai.music.dao.mapper.SongMapper;
import com.sallai.music.module.entity.SongEntity;
import com.sallai.music.module.vo.SongVo;
import com.sallai.music.module.vo.UserVo;
import com.sallai.music.utils.JwtUtil;
import com.sallai.music.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sallai.music.utils.OkHttpUtil;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName MusicService
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 23:29 2024/3/11
 * @Version 1.0
 **/
@Service
public class MusicService {
    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    OkHttpUtil okHttpUtil;

    @Autowired
    SongMapper songMapper;

    @Autowired
    SongService songService;

    @Autowired
    JwtUtil jwtUtil;

    private void handlerBin(ResponseBody resp) {
        InputStream inputStream = resp.byteStream();
        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            // 在http响应中输出流
            byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = inputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, nRead);
                outputStream.flush();
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            resp.close();

        }

    }

    /**
     * 输入音乐url，进行下载代理
     * @param url 音乐url
     */
    public void downLoad(String  url) {
        Response response = null;
        try {
            response = okHttpUtil.sendGetRequest(url, new HashMap<>(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response != null;
        httpServletResponse.setContentType("audio/mpeg");
        handlerBin(Objects.requireNonNull(response.body()));
    }

    /**
     * 分页查询收藏歌曲
     * @param pageNum
     * @param size
     * @return
     */
    public PageResult<SongVo> getLikeSongList(int pageNum, int size) {
        UserVo verify = jwtUtil.verify();
        Page<SongEntity> page = new Page<>(pageNum, size);
        IPage<SongEntity> pageResult = songMapper.queryLikeSongByPage(page,verify.getId());
        List<SongVo> songVos = pageResult.getRecords().stream().map(SongVo::convert).collect(Collectors.toList());
        PageResult<SongVo> convert = PageResult.convert(pageResult, SongVo.class);
        convert.setContent(songVos);
        return convert;
    }

    /**
     * 添加喜欢歌曲
     * @param songVo
     */
    @Transactional(rollbackFor = {Exception.class})
    public void addLikeSong(List<SongVo> songVos) {
        UserVo verify = jwtUtil.verify();
        List<SongEntity> entities = new ArrayList<>();
        for (SongVo songVo : songVos) {
            SongEntity songEntity = SongEntity.builder().id(UUID.randomUUID().toString())
                    .musicId(songVo.getMusicId())
                    .name(songVo.getTitle())
                    .artist(songVo.getArtist())
                    .playUrl(songVo.getPlayUrl())
                    .sourceType(songVo.getSourceType())
                    .userId(verify.getId()).build();
            entities.add(songEntity);
        }
        songService.saveBatch(entities);
    }

    /**
     * 移除喜欢歌曲
     * @param ids
     */
    @Transactional(rollbackFor = {Exception.class})
    public int removeLikeSong(List<String> ids) {
        UserVo verify = jwtUtil.verify();
        int delete = songMapper.delete(songService.query().eq("user_id", verify.getId())
                .in("id", ids).getWrapper());
        return delete;
    }
}

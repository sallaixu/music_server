package com.sallai.music.controller;

import java.io.IOException;
import java.util.List;

import com.sallai.music.module.vo.SongVo;
import com.sallai.music.module.vo.UserVo;
import com.sallai.music.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sallai.music.bean.MusicInfoBean;
import com.sallai.music.bean.MusicListVo;
import com.sallai.music.server.music.BaseMusicInterface;
import com.sallai.music.server.music.MusicInstanceFactory;
import com.sallai.music.server.music.MusicService;
import com.sallai.music.utils.RR;


/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/9/8
 * @author sallai
 */
@RestController
@RequestMapping("/_api/music")
@CrossOrigin
public class MusicController {
    @Autowired
    MusicService musicService;


    @GetMapping("/search/{type}/{keyword}/{size}")
    public RR playNext(@PathVariable(name = "type") String type, @PathVariable(name = "keyword") String keyword,
                       @PathVariable(name = "size") int size) {
        BaseMusicInterface musicInterface = MusicInstanceFactory.getInstance(type);
        MusicListVo musicListVo = musicInterface.searchMusic(keyword, size);
        return RR.ok(musicListVo);
    }
    @GetMapping("/detail/{type}/{id}")
    public RR getMusicDetail(@PathVariable String type, @PathVariable String id) {
        BaseMusicInterface musicInterface = MusicInstanceFactory.getInstance(type);
        MusicInfoBean musicDetailInfo = musicInterface.getMusicDetailInfo(id);
        return RR.ok(musicDetailInfo);
    }

    @GetMapping("/download")
    public void getMusicDetail(@RequestParam String url) throws IOException {
        musicService.downLoad(url);
    }

    @GetMapping("/like/list")
    public RR getLikeSongList(@RequestParam int page, @RequestParam int size)  {
        PageResult<SongVo> pageResult = musicService.getLikeSongList(page,size);
        return RR.ok(pageResult);
    }

    @PostMapping("/like/add")
    public RR addLikeSong(@RequestBody @Validated List<SongVo> songVos)  {
        musicService.addLikeSong(songVos);
        return RR.ok().setMsg("添加成功");
    }

    @PostMapping("/like/remove")
    public RR removeLikeSong(@RequestBody @Validated List<String> ids)  {
        int count = musicService.removeLikeSong(ids);
        return RR.ok().setMsg("移除成功:" + count);
    }
}

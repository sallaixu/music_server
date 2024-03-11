package com.sallai.music.controller;

import com.sallai.music.bean.MusicInfoBean;
import com.sallai.music.bean.MusicListVo;
import com.sallai.music.module._enum.MusicServiceEnum;
import com.sallai.music.module.pojo.DownLoadInfoPojo;
import com.sallai.music.server.music.AbstractMusic;
import com.sallai.music.server.music.BaseMusicInterface;
import com.sallai.music.server.music.MusicInstanceFactory;
import com.sallai.music.server.music.MusicService;
import com.sallai.music.server.music.impl.BabyMusic;
import com.sallai.music.server.music.impl.NetEasyMusic;
import com.sallai.music.server.music.impl.SliderKzMusic;
import com.sallai.music.utils.Http;
import com.sallai.music.utils.OkHttpUtil;
import com.sallai.music.utils.RR;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


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
}

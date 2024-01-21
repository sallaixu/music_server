package com.sallai.music.controller;

import com.sallai.music.bean.MusicListVo;
import com.sallai.music.module._enum.MusicServiceEnum;
import com.sallai.music.server.music.BaseMusicInterface;
import com.sallai.music.server.music.impl.BabyMusic;
import com.sallai.music.server.music.impl.NetEasyMusic;
import com.sallai.music.server.music.impl.SliderKzMusic;
import com.sallai.music.utils.RR;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/search/{type}/{keyword}/{size}")
    public RR playNext(@PathVariable(name = "type") String type, @PathVariable(name = "keyword") String keyword,
                       @PathVariable(name = "size") int size) {
        MusicServiceEnum musicServiceEnum = MusicServiceEnum.valueOf(type);
        BaseMusicInterface musicInterface = null;
        switch (musicServiceEnum) {
            case BABY_MUSIC:
                musicInterface = new BabyMusic();break;
            case NET_EASY:
                musicInterface = new NetEasyMusic();break;
            case SLIDER_KZ:
                musicInterface = new SliderKzMusic();break;
            default:
                musicInterface = new SliderKzMusic();break;
        }
        MusicListVo musicListVo = musicInterface.searchMusic(keyword, size);
        return RR.ok(musicListVo);
    }
}

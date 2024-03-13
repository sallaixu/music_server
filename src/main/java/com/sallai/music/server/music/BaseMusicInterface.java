package com.sallai.music.server.music;

import com.sallai.music.bean.MusicInfoBean;
import com.sallai.music.bean.MusicListVo;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/2
 * @author sallai
 */
public interface BaseMusicInterface {

    /**
     * 搜索音乐接口
     * @param keyword
     * @param size
     * @return
     */
    MusicListVo searchMusic(String keyword, int size);

    MusicInfoBean getMusicDetailInfo(String musicId);

}

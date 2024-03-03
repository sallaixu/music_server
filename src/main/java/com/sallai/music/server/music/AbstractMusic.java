package com.sallai.music.server.music;

import com.sallai.music.bean.MusicInfoBean;
import com.sallai.music.module._enum.MusicServiceEnum;
import com.sallai.music.server.music.impl.BabyMusic;
import com.sallai.music.server.music.impl.NetEasyMusic;
import com.sallai.music.server.music.impl.SliderKzMusic;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


import static com.sallai.music.utils.AppConstant.RQUEST_URL_TIMEOUT;


/**
 * Description:
 * Author: sallai
 * Date: 2023/9/23
 * Email: sallai@aliyun.com
 * @author sallai
 */

public abstract class AbstractMusic implements BaseMusicInterface {

    public void waitRequestOkForTime(CountDownLatch countDownLatch, List<MusicInfoBean> musicList) {
        try {
            boolean await = countDownLatch.await(RQUEST_URL_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<MusicInfoBean> removeList = new ArrayList<>();
        for (MusicInfoBean musicInfoBean : musicList) {
            if(StringUtils.isEmpty(musicInfoBean.getUrl())) {
                removeList.add(musicInfoBean);
            }
        }
        musicList.removeAll(removeList);
    }


    @Override
    public MusicInfoBean getMusicDetailInfo(String musicId) {
        return new MusicInfoBean();
    }


}

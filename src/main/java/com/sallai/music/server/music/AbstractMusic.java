package com.sallai.music.server.music;

import static com.sallai.music.utils.AppConstant.RQUEST_URL_TIMEOUT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.sallai.music.bean.MusicInfoBean;


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

    public String subString(String start,String end,String content) {
        int startIndex = "".equals(start) ? 0 : content.indexOf(start);
        int stopIndex = content.indexOf(end, startIndex + start.length());
        return content.substring(startIndex + start.length(), stopIndex);
    }


}

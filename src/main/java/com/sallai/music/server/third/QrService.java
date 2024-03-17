package com.sallai.music.server.third;

import com.alibaba.fastjson.JSON;
import com.sallai.music.dao.mapper.UserMapper;
import com.sallai.music.exception.QrLoginException;
import com.sallai.music.module.entity.UserEntity;
import com.sallai.music.module.pojo.QrCallBackPojo;
import com.sallai.music.server.SocketService;
import com.sallai.music.utils.JwtUtil;
import com.sallai.music.utils.OkHttpUtil;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

import static com.sallai.music.utils.AppConstant.QR_URL;

/**
 * @ClassName QrService
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 11:45 PM 3/15/2024
 * @Version 1.0
 **/
@Service
public class QrService {
    @Autowired
    private OkHttpUtil okHttpUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;


    public <T> T getQrLoginUrl(Class<T> t) throws IOException {
        Response response = okHttpUtil.sendGetRequest(QR_URL, new HashMap<>());
        if (response.code() == HttpStatus.OK.value()) {
            String string = Objects.requireNonNull(response.body()).string();
            return JSON.parseObject(string, t);
        }
        return null;
    }

    /**
     * 处理微信扫码登录
     * @param pojo
     */
    public void qrLogin(QrCallBackPojo pojo) {
        String tempUserId = pojo.getTempUserId();
        String socketId = redisTemplate.opsForValue().getAndDelete(tempUserId);
        if(socketId == null) {
            throw new QrLoginException("请刷新二维码，重新登录");
        }
        Map<String, String> map = new HashMap<>(1);

        UserEntity userEntity = userMapper.selectById(pojo.getUserId());
        if(null == userEntity) {
            //注册
            userEntity = UserEntity.builder()
                    .name(StringUtils.hasText(pojo.getNickname()) ? pojo.getNickname() : genUserName())
                    .avatar(pojo.getAvatar()).firstLoginTime(new Date()).lastLoginTime(new Date())
                    .id(pojo.getUserId()).ipAddr(pojo.getIpAddr()).build();
            userMapper.insert(userEntity);
        }else {
            userEntity.setLastLoginTime(new Date());
            userMapper.updateById(userEntity);
        }
        map.put("userId", userEntity.getId());
        String token = JwtUtil.getToken(map);
        SocketService.sendToUser(socketId,"token",token);
    }

    /**
     * 创建用户名
     * @return
     */
    public String genUserName() {
        return "user-" + UUID.randomUUID().toString().split("-")[0];
    }

}

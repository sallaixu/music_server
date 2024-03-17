package com.sallai.music.server.user;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sallai.music.dao.mapper.UserMapper;
import com.sallai.music.module.entity.UserEntity;
import com.sallai.music.module.vo.UserVo;
import com.sallai.music.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 1:57 PM 3/16/2024
 * @Version 1.0
 **/
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * 查询用户信息
     * @return
     */
    public UserVo queryUserInfoById() {
        UserVo userVo = jwtUtil.verify();
        UserEntity userEntity = userMapper.selectById(userVo.getId());
        UserVo convert = UserVo.convert(userEntity);
        return convert;
    }
}

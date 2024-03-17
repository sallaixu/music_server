package com.sallai.music.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sallai.music.exception.TokenException;
import com.sallai.music.module.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Map;

/**
 * @ClassName JwtUtil
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 11:39 AM 3/16/2024
 * @Version 1.0
 **/
@Component
public class JwtUtil {
    @Autowired
    private HttpServletRequest servletRequest;
    /**
     * 秘钥自己保管好
     */
    private static String SECRET = "fj23elk,.//3452ss";
    /**
     * 生成token
     * @param map  //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        Calendar instance = Calendar.getInstance();
        //默认3天过期
        instance.add(Calendar.DATE,3);
        //指定过期时间
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public DecodedJWT verify(String token){
        // 有任何验证异常，此处都会抛出
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    public UserVo verify() {
        String token = servletRequest.getHeader("token");
        if(!StringUtils.hasText(token)) { throw new TokenException(); }
        DecodedJWT verify = verify(token);
        Claim id = verify.getClaim("userId");
        if(id.isNull()) {
            throw new TokenException();
        }
        UserVo userVo = UserVo.builder().id(id.asString()).build();
        return userVo;
    }

    /**
     * 获取token中payload数据
     * @param token
     * @return
     */
    public static Map<String, Claim> getPayloadFromToken(String token) {
        return  JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token).getClaims();
    }

}
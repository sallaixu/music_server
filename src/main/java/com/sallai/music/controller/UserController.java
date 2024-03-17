package com.sallai.music.controller;

import com.sallai.music.module.pojo.QrCallBackPojo;
import com.sallai.music.module.vo.QrStateVo;
import com.sallai.music.module.vo.QrUrlPojo;
import com.sallai.music.module.vo.UserVo;
import com.sallai.music.server.third.QrService;
import com.sallai.music.server.user.UserService;
import com.sallai.music.utils.RR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 9:28 PM 3/15/2024
 * @Version 1.0
 **/
@RestController
@RequestMapping("/_api/user/")
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    QrService qrService;

    @Autowired
    UserService userService;

    @PostMapping("qr")
    public QrStateVo qrLogin(QrCallBackPojo pojo) {
        log.info("pojo {}",pojo);
        qrService.qrLogin(pojo);
        return QrStateVo.builder().errcode(0).message("登录成功").build();
    }

    @GetMapping("qr/url")
    public RR qrLoginUrl() throws IOException {
        QrUrlPojo qrLoginUrl = qrService.getQrLoginUrl(QrUrlPojo.class);
        return RR.ok(qrLoginUrl);
    }

    @GetMapping("info")
    public RR getUserInfo()  {
        UserVo userVo = userService.queryUserInfoById();
        return RR.ok(userVo);
    }



}

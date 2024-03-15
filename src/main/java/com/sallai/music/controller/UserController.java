package com.sallai.music.controller;

import com.sallai.music.module.pojo.QrCallBackPojo;
import com.sallai.music.module.vo.QrStateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
public class UserController {

    @PostMapping("qr")
    public QrStateVo qrLogin(QrCallBackPojo pojo) {
        log.info("pojo {}",pojo);
        return QrStateVo.builder().errcode(0).message("登录成功").build();
    }
}

package com.sallai.music.module._enum;

import lombok.Getter;

/**
 * Description: 错误状态枚举
 * Author: sallai
 * Date: 2023/9/23
 * Email: sallai@aliyun.com
 */
@Getter
public enum  ErrorEnum {
    //未知错误
    UNKNOW("未知错误!",1000),
    //文件未找到异常
    NOT_FOUND("文件未找到异常",1001),
    //参数非法
    INVALID_PARAM("参数非法",1002),
    //未授权异常
    UNAUTHROIZED("未授权异常",1003),
    //服务端错误
    SERVER_ERROR("服务端错误",1004);


    private final String msg;
    private final Integer code;

    private ErrorEnum(String name,Integer code) {
        this.msg = name;
        this.code = code;
    }
}

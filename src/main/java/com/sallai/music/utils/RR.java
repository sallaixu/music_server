package com.sallai.music.utils;

import com.alibaba.fastjson.JSON;
import com.sallai.music.module._enum.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/5/14
 */
@Data
@AllArgsConstructor
public class RR implements Serializable {
    private int code;
    private String msg;
    private String errorType;
    private Object data;

    public RR setCode(int code) {
        this.code = code;
        return this;
    }

    public RR setErrorType(String errorType) {
        this.errorType = errorType;
        return this;
    }

    public RR setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public RR setData(Object data) {
        this.data = data;
        return this;
    }

    public RR() {
        this.code = 200;
        this.msg = "success";
    }

    public RR(Object data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }

    public RR(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    // getter 和 setter 略

    public static RR ok() {
        return new RR();
    }

    public static  RR ok(Object data) {
        return new RR(data);
    }

    public static  RR error(int code, String msg) {
        return new RR(code, msg);
    }

    public static  RR error(StateCode code) {
        return new RR(code.code, code.message);
    }

    public static RR StateEnum(StateCode errorEnum) {
        RR r = RR.error(errorEnum.code, errorEnum.message);
        r.setErrorType(errorEnum.message);
        return r;
    }


    public static enum StateCode {
        /**
         * QR 登录异常
         */
        QR_LOGIN_EXPIRE(1,"QR 登录异常"),
        /**
         * token 过期
         */
        TOKEN_EXPIRE(201,"token 过期"),
        /**
         * 未登录
         */
        TOKEN_ABSENT(202,"未登录"),
        /**
         * 未知错误
         */
        UNKNOW(211,"未知错误"),
        /**
         * 运行时错误
         */
        RUNTIME_EXCEPTION(212,"运行时异常"),
        /**
         * 成功
         */
        ATTR_EXCEPTION(221,"参数绑定异常"),
        /**
         * 成功
         */
        SUCCESS(200,"成功");

        public final int code;
        public final String message;

        private StateCode(int code,String message) {
            this.code =  code;
            this.message = message;
        }
    }

}
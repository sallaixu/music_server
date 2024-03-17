package com.sallai.music.exception;

/**
 * @ClassName TokenException
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 10:36 PM 3/16/2024
 * @Version 1.0
 **/
public class TokenException extends RuntimeException{
    public TokenException(String message) {
        super(message);
    }

    public TokenException() {
        super();
    }
}

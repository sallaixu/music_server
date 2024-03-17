package com.sallai.music.exception;

/**
 * @ClassName TokenException
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 10:36 PM 3/16/2024
 * @Version 1.0
 **/
public class QrLoginException extends RuntimeException{
    public QrLoginException(String message) {
        super(message);
    }

    public QrLoginException() {
        super();
    }
}

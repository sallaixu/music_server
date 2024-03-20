package com.sallai.music.configuraiton;

import com.sallai.music.exception.QrLoginException;
import com.sallai.music.exception.TokenException;
import com.sallai.music.module.vo.QrStateVo;
import com.sallai.music.utils.RR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @ClassName GlobalException
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 10:38 PM 3/16/2024
 * @Version 1.0
 **/
@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(value = {TokenException.class})
    public RR tokenException(TokenException e) {
        log.error(e.getLocalizedMessage());
        e.printStackTrace();
        return RR.StateEnum(RR.StateCode.TOKEN_EXPIRE).setMsg(e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public RR runTimeException(RuntimeException e) {
        e.printStackTrace();
        log.error(e.toString());
        return RR.StateEnum(RR.StateCode.RUNTIME_EXCEPTION).setMsg(e.getLocalizedMessage().toLowerCase(Locale.ROOT));
    }

    @ExceptionHandler(value = {QrLoginException.class})
    public QrStateVo qrLoginException(QrLoginException e) {
        log.error(e.getLocalizedMessage());
        e.printStackTrace();
        return QrStateVo.builder().errcode(RR.StateCode.QR_LOGIN_EXPIRE.code).message(e.getLocalizedMessage()).build();
    }

    @ExceptionHandler(value = {BindException.class, ConstraintViolationException.class})
    public RR handleException1(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        log.error(message);
        return RR.StateEnum(RR.StateCode.ATTR_EXCEPTION).setMsg(message);
    }

}

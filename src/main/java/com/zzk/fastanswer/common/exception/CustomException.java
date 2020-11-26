package com.zzk.fastanswer.common.exception;

import com.zzk.fastanswer.common.enums.CustomExceptionType;

/**
 * 业务异常类
 *
 * @author zzk
 * @create 2020-11-04 21:13
 */
public class CustomException extends RuntimeException {
    /**
     * 异常错误编码
     */
    private int code;

    /**
     * 异常信息
     */
    private String message;

    private CustomException() {
    }

    public CustomException(CustomExceptionType exceptionTypeEnum) {
        this.code = exceptionTypeEnum.getCode();
        this.message = exceptionTypeEnum.getDesc();
    }

    public CustomException(CustomExceptionType exceptionTypeEnum,
                           String message) {
        this.code = exceptionTypeEnum.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package com.javaoffers.brief.modelhelper.exception;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/9 11:33
 */
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.javaoffers.batis.modelhelper.exception;

/**
 * @author mingJie
 */
public class ParseParamException extends RuntimeException {

    public ParseParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseParamException(String message) {
        super(message);
    }
}

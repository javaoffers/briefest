package com.javaoffers.brief.modelhelper.exception;

/**
 * @author mingJie
 */
public class ParseModelException extends RuntimeException {

    public ParseModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseModelException(String message) {
        super(message);
    }
}

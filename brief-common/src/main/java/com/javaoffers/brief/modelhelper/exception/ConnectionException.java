package com.javaoffers.brief.modelhelper.exception;

/**
 * @author mingJie
 */
public class ConnectionException extends RuntimeException {
    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
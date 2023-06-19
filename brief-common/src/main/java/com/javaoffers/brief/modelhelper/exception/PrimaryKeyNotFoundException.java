package com.javaoffers.brief.modelhelper.exception;

/**
 * @description:
 * @author: create by cmj on 2023/6/19 22:43
 */
public class PrimaryKeyNotFoundException  extends RuntimeException {
    public PrimaryKeyNotFoundException(String message) {
        super(message);
    }
}

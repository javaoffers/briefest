package com.javaoffers.batis.modelhelper.exception;

/**
 * @Description: Error looking up field.
 * @Auther: create by cmj on 2022/6/11 23:11
 */
public class EnumValueException extends RuntimeException {
    public EnumValueException(String message) {
        super(message);
    }
}

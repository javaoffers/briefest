package com.javaoffers.batis.modelhelper.exception;

/**
 * @Description: 查找字段时出错.
 * @Auther: create by cmj on 2022/6/11 23:11
 */
public class FindColException extends RuntimeException {
    public FindColException(String message) {
        super(message);
    }
}

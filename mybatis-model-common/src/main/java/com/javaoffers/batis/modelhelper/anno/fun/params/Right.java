package com.javaoffers.batis.modelhelper.anno.fun.params;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: RIGHT(colName,len)
 * @author: create by cmj on 2022/10/15 15:09
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Right {
    public static final String TAG = "RIGHT";
    int value();
}

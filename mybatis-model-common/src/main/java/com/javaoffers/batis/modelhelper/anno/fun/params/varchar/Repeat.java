package com.javaoffers.batis.modelhelper.anno.fun.params.varchar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: REPEAT(str,x) The number of times used to repeat a string.
 * @author: create by cmj on 2022/10/15 15:15
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Repeat {
    public static final String TAG = "REPEAT";
    int value();
}

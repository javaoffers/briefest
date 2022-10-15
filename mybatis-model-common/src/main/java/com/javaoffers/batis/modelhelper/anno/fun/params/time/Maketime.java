package com.javaoffers.batis.modelhelper.anno.fun.params.time;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:  maketime(colName ,minute, second)
 * select maketime(12,15,30); -- '12:15:30'
 * @author: create by cmj on 2022/10/15 17:07
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Maketime {
    public static final String TAG = "MAKETIME";

    /**
     * minute
     */
    int ep1();

    /**
     * second
     */
    int ep2();
}

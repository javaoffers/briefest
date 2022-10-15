package com.javaoffers.batis.modelhelper.anno.fun.params.time;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: sec_to_time(colName)
 * @author: create by cmj on 2022/10/15 16:48
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SecToTime {

    public static final String TAG = "SEC_TO_TIME";
}

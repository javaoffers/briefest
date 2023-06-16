package com.javaoffers.brief.modelhelper.anno.fun.params.time;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: time_to_sec(colName)  Converts a time value to a second value
 * TIME_TO_SEC('15:15:15') output 54915
 * @author: create by cmj on 2022/10/15 16:41
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TimeToSec {
    public static final String TAG = "TIME_TO_SEC";
}

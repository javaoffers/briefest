package com.javaoffers.batis.modelhelper.anno.fun.params.time;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: from_days(colName)
 * Returns a date from a numeric date value
 *   SELECT FROM_DAYS(780500); output 2136-12-08
 * @author: create by cmj on 2022/10/15 16:37
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FromDays {
    public static final String TAG = "FROM_DAYS";
}

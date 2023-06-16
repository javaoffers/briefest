package com.javaoffers.brief.modelhelper.anno.fun.params.time;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: makedate(colName, days)
 * select makedate(2001,31); -- '2001-01-31'
 * select makedate(2001,32); -- '2001-02-01'
 * @author: create by cmj on 2022/10/15 17:03
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Makedate {
    public static final String TAG = "MAKEDATE";

    /**
     * days
     */
    int value();
}

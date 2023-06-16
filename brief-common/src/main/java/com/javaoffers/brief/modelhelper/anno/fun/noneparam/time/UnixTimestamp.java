package com.javaoffers.brief.modelhelper.anno.fun.noneparam.time;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: unix_timestamp();
 * @author: create by cmj on 2022/10/15 17:01
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UnixTimestamp {
    public static final String TAG = "UNIX_TIMESTAMP";
}

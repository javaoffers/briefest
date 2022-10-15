package com.javaoffers.batis.modelhelper.anno.fun.params.varchar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: RTRIM(str)
 * @author: create by cmj on 2022/10/15 15:15
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RTrim {
    public static final String TAG = "RTRIM";
}

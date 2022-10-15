package com.javaoffers.batis.modelhelper.anno.fun.params.time;

import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HOUR(colName)
 * SELECT HOUR('10:05:03'); output 10
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Hour {
    public static final String TAG = "HOUR";

}

package com.javaoffers.batis.modelhelper.anno.fun.params.time;

import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MINUTE(colName)
 * select minute('2018-9-28 10:20:25.000002') ; 20
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Minute {
    public static final String TAG = "MINUTE";

}

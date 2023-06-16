package com.javaoffers.brief.modelhelper.anno.fun.params.math;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * https://blog.csdn.net/weixin_51696091/article/details/124731621
 * Absolute value function: abs(colName)
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Abs {
    public static final String TAG = "ABS";

}

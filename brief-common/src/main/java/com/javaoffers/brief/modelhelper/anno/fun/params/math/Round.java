package com.javaoffers.brief.modelhelper.anno.fun.params.math;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ROUND(colName, precision)
 *  ROUND(20.5,0) 21
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Round {
    public static final String TAG = "ROUND";

    /**
     * Accuracy
     */
    int precision() default 0;
}

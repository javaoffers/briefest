package com.javaoffers.brief.modelhelper.anno.fun.params.math;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  TRUNCATE(colName, precision)
 *  TRUNCATE(7.536432,2); 7.53
 * Numerical intercept function
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Truncate {

    public static final String TAG = "TRUNCATE";

    /**
     * Preserve the length after the decimal point.
     * value of precision
     */
    int value() default 0;
}

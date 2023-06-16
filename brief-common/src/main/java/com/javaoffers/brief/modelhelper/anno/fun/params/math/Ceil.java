package com.javaoffers.brief.modelhelper.anno.fun.params.math;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * select CEIL(colName)
 * CEIL(3.2) : 4
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Ceil {

    public static final String TAG = "CEIL";

}

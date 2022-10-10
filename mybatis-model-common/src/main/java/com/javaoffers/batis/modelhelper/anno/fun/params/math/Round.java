package com.javaoffers.batis.modelhelper.anno.fun.params.math;

import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Round {
    public static final String TAG = "ROUND";

    /**
     * Defaults to field name of table
     */
    String value() default ModelHelpperConstants.FIELD_COL_NAME;

    /**
     * To keep a few decimal places
     */
    int precision() default 0;
}

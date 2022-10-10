package com.javaoffers.batis.modelhelper.anno.fun.params.math;

import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * select CEIL(X)
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Ceil {

    public static final String TAG = "CEIL";

    /**
     * Defaults to field name of table
     */
    String value() default ModelHelpperConstants.FIELD_COL_NAME;
}

package com.javaoffers.batis.modelhelper.anno.fun.params.varchar;

import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * lower(x)
 * create by mingjie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Lower {
    public static final String TAG = "LOWER";
    /**
     * Defaults to field name of table
     */
    String value() default ModelHelpperConstants.FIELD_COL_NAME;
}

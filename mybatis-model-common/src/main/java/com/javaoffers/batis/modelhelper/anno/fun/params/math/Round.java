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
     * The table field corresponding to field. The default is filed name underscore format
     * You can also modify the value of value to change the table field corresponding to field. It is the modified value.
     * Functions like {@link com.javaoffers.batis.modelhelper.anno.ColName}.
     * Note: When using {@code @ColName}. on field to specify table field information. value here will be ignored
     * (even if value has been modified).
     */
    int precision() default 0;
}

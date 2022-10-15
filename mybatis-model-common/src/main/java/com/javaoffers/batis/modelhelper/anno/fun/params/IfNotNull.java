package com.javaoffers.batis.modelhelper.anno.fun.params;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * extended if: if (colName is not null, value, colName).
 * Can be combined with {@link IfNull}
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IfNotNull {
    public static String EXPR = " is not null ";
    /**
     * When not null, output this value
     */
    String value();

    /**
     * when null. output null by default.
     */
    String ifNull() default "null";
}

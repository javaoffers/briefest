package com.javaoffers.batis.modelhelper.anno.fun.params;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * extended if: if (colName < xx, xx, xx)
 * colName < xx
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IfLt {
    public static String EXPR = " < ";
    /**
     * colName less than xx.
     */
    String lt();
    /**
     * expr1
     */
    String ep1();

    /**
     * expr2
     */
    String ep2();
}

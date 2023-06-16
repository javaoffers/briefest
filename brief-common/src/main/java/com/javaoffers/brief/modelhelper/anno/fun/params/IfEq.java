package com.javaoffers.brief.modelhelper.anno.fun.params;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * extended if: if (colName = xx, ep1, ep2)
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IfEq {
    public static String EXPR = " = ";
    /**
     * colName equal xx.
     */
    String eq();
    /**
     * expr1
     */
    String ep1();

    /**
     * expr2
     */
    String ep2();
}

package com.javaoffers.batis.modelhelper.anno.fun.params;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * extended if: if (colName <= xx, ep1, ep2)
 * colName <= xx
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IfLte {
    public static String EXPR = " <= ";
    /**
     * colName less than or equal xx.
     */
    String lte();
    /**
     * expr1
     */
    String ep1();

    /**
     * expr2
     */
    String ep2();
}

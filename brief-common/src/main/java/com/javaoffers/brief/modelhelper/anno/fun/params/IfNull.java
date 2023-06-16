package com.javaoffers.brief.modelhelper.anno.fun.params;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ifNull(colName, 'defalut value').
 * sample:
 * <p>
 *     @ColName("name")
 *     @IfNull("'Amop'")
 *     private String colName;
 * <p/>
 * create by cmj
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IfNull {
    public static final String TAG = "IFNULL";
    /**
     * defalut value
     */
    String value() ;
}

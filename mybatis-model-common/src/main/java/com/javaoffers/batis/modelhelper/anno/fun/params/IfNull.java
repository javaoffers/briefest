package com.javaoffers.batis.modelhelper.anno.fun.params;

import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ifNull(colName, 'xxx')
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

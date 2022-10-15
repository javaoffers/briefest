package com.javaoffers.batis.modelhelper.anno.fun.params.varchar;

import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * concat(colName,xx..) as fieldName : Merge string function
 * create by cmj
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Concat {
    public static final String TAG = "CONCAT";

    /**
     * Additional table field information to be spliced
     */
    String[] value() ;
}

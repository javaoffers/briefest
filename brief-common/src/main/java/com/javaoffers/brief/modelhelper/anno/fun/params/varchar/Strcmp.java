package com.javaoffers.brief.modelhelper.anno.fun.params.varchar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * strcmp(colName,expr): compareStringSizeFunction
 * create by cmj
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Strcmp {
    public static final String TAG = "STRCMP";

    /**
     * expr
     */
    String value();
}

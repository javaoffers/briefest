package com.javaoffers.batis.modelhelper.anno.fun.params.varchar;

import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * concat(xx,xx..) as fieldName : Merge string function
 * create by cmj
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Concat {
    public static final String TAG = "CONCAT";

    /**
     * The table field corresponding to field. The default is filed name underscore format
     * You can also modify the value of value to change the table field corresponding to field. It is the modified value.
     * Functions like {@link com.javaoffers.batis.modelhelper.anno.ColName}.
     * Note: When using {@code @ColName}. on field to specify table field information. value here will be ignored
     * (even if value has been modified).
     */
    String value() default ModelHelpperConstants.FIELD_COL_NAME;

    /**
     * Additional table field information to be spliced
     */
    String[] colNames() ;
}

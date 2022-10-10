package com.javaoffers.batis.modelhelper.anno;

import java.lang.annotation.*;

/**
 * for model data
 * @author cmj
 *
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BaseModel {

    /**
     * Specify the table name. If not, it defaults to the class name with camel case and underscore
     * Note: This property is useful in CrudMapper scenarios
     * @return
     */
    String value() default "";

}

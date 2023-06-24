package com.javaoffers.brief.modelhelper.anno;

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
     */
    String value() default "";

    /**
     * Update automatically identify differences.
     * If open. When you call a setter method automatically refresh the db
     */
    boolean autoUpdate() default false;

}

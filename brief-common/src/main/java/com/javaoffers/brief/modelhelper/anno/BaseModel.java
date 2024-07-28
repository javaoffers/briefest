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
     * support with as ....
     * sample:
     * with t_table as ( select age, name from table_name where xx.. ).
     *
     * sample:
     * select age, name from table_name where xx..
     * union all
     * select age, name from table_name2 where xx..
     *
     * @return
     */
    String frontView() default "";

    /**
     * support child select...
     * sample:
     *
     * select age, name from (select age, name from table_name where xx..)
     *
     * @return
     */
    String fromView() default "";

}

package com.javaoffers.brief.modelhelper.anno;

import java.lang.annotation.*;

/**
 * Specify the database field name. Or a fragment of a raw query statement
 * Used on the attributes of the model class, this annotation can be used to
 * specify when the attribute name and field name are different
 * @Description: Aliases are used for CrudMapper
 * @Auther: create by cmj on 2022/5/3 00:16
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ColName  {

    /**
     * Table field name or a fragment of a raw query statement.
     * The property name will be used as an alias.
     * select  ( value ) as fieldName  from table.
     * Note: Changing the value of the annotation will replace the value in @BaseUnique,
     * if both annotations are used at the same time
     * @return anotherName
     */
    String value();

    /**
     * default false . if as true when execute the select.colAll(),insert.colAll(Model), update.colAll(Model)
     * will filter out this fields and colName.
     * note: fun sql will also filter out this fields and colName when execute the
     * insert.colAll(Model),
     * update.colAll(Model). but select.colAll() not .
     *       .
     * @return
     */
    boolean excludeColAll() default false;
}

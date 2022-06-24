package com.javaoffers.batis.modelhelper.anno;

import java.lang.annotation.*;

/**
 * @Description: 别名用于 CrudMapper ， 将表中的字段进行 as 属性名
 * @Auther: create by cmj on 2022/5/3 00:16
 */
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AliasName {

    /**
     * 表字段名称。 会将属性名 作为别名。
     * select  value as fieldName  from table
     * @return 别名
     */
    String value() ;
}
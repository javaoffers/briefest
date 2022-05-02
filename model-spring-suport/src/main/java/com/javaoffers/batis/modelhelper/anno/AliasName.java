package com.javaoffers.batis.modelhelper.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 别名用于 CrudMapper ， 将表中的字段进行 as 属性名
 * @Auther: create by cmj on 2022/5/3 00:16
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AliasName {

    /**
     * 表字段名称。 会将属性名 作为别名。
     * select  value as fieldName  from table
     * @return
     */
    String value() ;
}

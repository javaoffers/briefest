package com.javaoffers.batis.modelhelper.anno;

import java.lang.annotation.*;

/**
 * 指定数据库字段名称.
 * 用在model类的属性上，当属性名和字段名不同时可以使用该注解进行指定
 * @Description: 别名用于 CrudMapper
 * @Auther: create by cmj on 2022/5/3 00:16
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ColName {

    /**
     * 表字段名称。 会将属性名 作为别名。
     * select  value as fieldName  from table
     * @return 别名
     */
    String value() ;
}

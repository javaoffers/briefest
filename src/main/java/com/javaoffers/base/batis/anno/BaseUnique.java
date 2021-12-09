package com.javaoffers.base.batis.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 与BaseModel注解联合使用，BaseUnique用于字段上，用于确定model数据唯一
 * （用于描述在数据库表中确定唯一的一条数据）
 * @author cmj
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseUnique {

}

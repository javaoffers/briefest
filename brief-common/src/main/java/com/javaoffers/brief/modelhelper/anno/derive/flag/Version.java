package com.javaoffers.brief.modelhelper.anno.derive.flag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: Provide a version field and can be used for version updates.
 * @author: create by cmj on 2023/6/11 00:45
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Version {
}

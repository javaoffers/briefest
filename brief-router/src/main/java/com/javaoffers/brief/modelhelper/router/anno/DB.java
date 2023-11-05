package com.javaoffers.brief.modelhelper.router.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多数据源切换.
 * @author mingJie
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DB {

    //全局默认
    String value() default "GLOBAL_DEFAULT";

    //是否强制路由, 该机制实在开启主从同步场景下使用。
    boolean isForce() default  false;

}

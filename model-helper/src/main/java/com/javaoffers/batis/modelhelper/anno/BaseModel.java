package com.javaoffers.batis.modelhelper.anno;

import java.lang.annotation.*;

/**
 * 用于model数据
 * @author cmj
 *
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BaseModel {

    /**
     * 制定表名称. 如果没有则默认为类命名驼峰变下划线
     * 注意： 此属性在CrudMapper场景下有用
     * @return
     */
    String value() default "";

}

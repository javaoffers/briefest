package com.javaoffers.batis.modelhelper.anno;

import java.lang.annotation.*;

/**
 * 与BaseModel注解联合使用，BaseUnique用于字段上，用于确定model数据唯一
 * （用于描述在数据库表中确定唯一的一条数据）
 * @author cmj
 *
 */
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BaseUnique {
    /**
     * 表字段名称。 在生成sql时会将类属性名作为别名。 （该字段具有唯一属性）
     * 例如： select  value as fieldName  from table.
     * 缺省情况下默认为属性名称下划线格式作为表字段进行sql解析。
     * 注意： 此属性在CrudMapper场景下有用
     * 注意： 你也可以使用{@link @ColName(colName)}
     */
    String value() default "";
}

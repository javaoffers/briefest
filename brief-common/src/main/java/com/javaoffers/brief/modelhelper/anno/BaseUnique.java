package com.javaoffers.brief.modelhelper.anno;

import com.javaoffers.brief.modelhelper.core.KeyGenerate;
import com.javaoffers.brief.modelhelper.core.UniqueKeyGenerate;
import com.javaoffers.brief.modelhelper.core.VoidKey;

import java.lang.annotation.*;

/**
 * Used in conjunction with Base Model annotations, Base Unique is used on
 * fields to determine the uniqueness of model data
 * (used to describe a unique piece of data in a database table)
 * @author cmj
 *
 */
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BaseUnique {
    /**
     * Table field name. The class attribute name is used as an alias when generating sql. (the field has a unique attribute)
     * For example: select value as fieldName from table.
     * By default, the attribute name underscore format is used as a table field for sql parsing.
     * Note: This property is useful in CrudMapper scenarios
     * Note: You can also use {@link @ColName(colName)}, The value of colName will override the value here if both exist.
     * But Unique features of {@code BaseUnique} are not lost。
     */
    String value() default "";

    /**
     * Default key generator. VoidKey is an invalid key.
     * Usually controlled by the server itself.
     * @return
     */
    Class<? extends UniqueKeyGenerate> keyGenerateClass() default VoidKey.class;

    /**
     * Default key generator. VoidKey is an invalid key.
     * Usually controlled by the server itself.
     * @return
     */
    KeyGenerate keyGenerate() default KeyGenerate.VOID_KEY;
}

package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_LENGTH(colName[, path]) : The number of elements in a JSON document,
 * or at the given path. When {@link #value()} is omitted the whole document is counted.
 * <p>
 *   @ColName("extra_info") @JsonLength
 *   // → JSON_LENGTH(extra_info)
 *   @ColName("extra_info") @JsonLength("'$.tags'")
 *   // → JSON_LENGTH(extra_info, '$.tags')
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonLength {

    public static final String TAG = "JSON_LENGTH";

    /**
     * Optional JSON path. String-literal paths must include their own quotes,
     * e.g. "'$.tags'". Empty (default) means the whole document.
     */
    String value() default "";
}

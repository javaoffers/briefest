package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_TYPE(colName) : The type of a JSON value — one of
 * OBJECT, ARRAY, INTEGER, STRING, BOOLEAN, NULL, etc.
 * <p>
 *   @ColName("extra_info") @JsonType
 *   // → JSON_TYPE(extra_info)
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonType {

    public static final String TAG = "JSON_TYPE";
}

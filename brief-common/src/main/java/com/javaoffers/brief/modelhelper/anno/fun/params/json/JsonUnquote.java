package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_UNQUOTE(colName) : Unquotes a JSON value, producing a plain string.
 * <p>
 *   @ColName("extra_info") @JsonExtract("'$.nickName'") @JsonUnquote
 *   // → JSON_UNQUOTE(JSON_EXTRACT(extra_info, '$.nickName'))
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonUnquote {

    public static final String TAG = "JSON_UNQUOTE";
}

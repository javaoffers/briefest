package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_DEPTH(colName) : The maximum depth of a JSON document.
 * <p>
 *   @ColName("extra_info") @JsonDepth
 *   // → JSON_DEPTH(extra_info)
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonDepth {

    public static final String TAG = "JSON_DEPTH";
}

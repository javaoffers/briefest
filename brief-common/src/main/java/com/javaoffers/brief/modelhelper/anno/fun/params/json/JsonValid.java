package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_VALID(colName) : Returns 1 if the value is valid JSON, 0 otherwise.
 * <p>
 *   @ColName("extra_info") @JsonValid
 *   // → JSON_VALID(extra_info)
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonValid {

    public static final String TAG = "JSON_VALID";
}

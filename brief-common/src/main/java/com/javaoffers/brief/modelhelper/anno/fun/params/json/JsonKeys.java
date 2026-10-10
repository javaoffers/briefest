package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_KEYS(colName[, path]) : The keys of a JSON object, or the object at the
 * given path. When {@link #value()} is omitted the top-level object's keys are returned.
 * <p>
 *   @ColName("extra_info") @JsonKeys
 *   // → JSON_KEYS(extra_info)
 *   @ColName("extra_info") @JsonKeys("'$.address'")
 *   // → JSON_KEYS(extra_info, '$.address')
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonKeys {

    public static final String TAG = "JSON_KEYS";

    /**
     * Optional JSON path. String-literal paths must include their own quotes,
     * e.g. "'$.address'". Empty (default) means the top-level object.
     */
    String value() default "";
}

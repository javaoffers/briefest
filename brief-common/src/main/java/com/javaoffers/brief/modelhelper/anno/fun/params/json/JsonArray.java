package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_ARRAY(value[, value]...) : Constructs a JSON array. The annotated column
 * is one element; its position among the extra expressions is controlled by
 * {@link #position()}.
 * <p>
 * Literal values must include their own quotes.
 * <p>
 *   @ColName("name") @JsonArray(value = {"'prefix'"}, position = 1)
 *   // → JSON_ARRAY('prefix', name)
 *   @ColName("name") @JsonArray
 *   // → JSON_ARRAY(name)
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonArray {

    public static final String TAG = "JSON_ARRAY";

    /**
     * Additional element expressions. Literal values must include their own
     * quotes, e.g. {"'prefix'", "1"}.
     */
    String[] value() default {};

    /**
     * Position of colName among the extra expressions in {@link #value()}.
     * 0 means at the far left, -1 means at the far right.
     */
    int position() default 0;
}

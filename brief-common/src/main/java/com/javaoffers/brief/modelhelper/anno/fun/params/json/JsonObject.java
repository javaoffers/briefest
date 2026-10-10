package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_OBJECT(key, value[, key, value]...) : Constructs a JSON object from
 * key/value pairs. The annotated column is one of the values; its position
 * among the extra expressions is controlled by {@link #position()}.
 * <p>
 * Keys and literal values must include their own quotes.
 * <p>
 *   @ColName("name") @JsonObject(value = {"'name'"}, position = 1)
 *   // → JSON_OBJECT('name', name)
 *   @ColName("age") @JsonObject(value = {"'age'", "'desc'", "'n/a'"}, position = 1)
 *   // → JSON_OBJECT('age', age, 'desc', 'n/a')
 * <p>
 * Use {@link #position()} = 0 to place the column first, &lt; 0 to place it last.
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonObject {

    public static final String TAG = "JSON_OBJECT";

    /**
     * Additional key/value expressions. Keys and literal values must include
     * their own quotes, e.g. {"'name'", "'desc'"}.
     */
    String[] value() default {};

    /**
     * Position of colName among the extra expressions in {@link #value()}.
     * 0 means at the far left, -1 means at the far right.
     */
    int position() default 0;
}

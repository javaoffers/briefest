package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_QUOTE(colName) : Quotes a string as a JSON value by wrapping it with
 * double quotes and escaping interior characters.
 * <p>
 *   @ColName("name") @JsonQuote
 *   // → JSON_QUOTE(name)
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonQuote {

    public static final String TAG = "JSON_QUOTE";
}

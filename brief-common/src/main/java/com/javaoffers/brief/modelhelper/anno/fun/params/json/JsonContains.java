package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_CONTAINS(colName, candidate[, path]) : Returns 1 if the candidate is
 * contained in the target JSON document (optionally at {@code path}).
 * Useful in WHERE clauses for membership tests on JSON arrays.
 * <p>
 *   @ColName("tags") @JsonContains("'java'")
 *   // → JSON_CONTAINS(tags, 'java')
 *   @ColName("extra_info") @JsonContains("'{\"k\":\"v\"}'", "'$.meta'")
 *   // → JSON_CONTAINS(extra_info, '{"k":"v"}', '$.meta')
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonContains {

    public static final String TAG = "JSON_CONTAINS";

    /**
     * Candidate JSON value. String-literal candidates must include their own
     * quotes, e.g. "'java'", '{"k":"v"}'.
     */
    String value();

    /**
     * Optional JSON path. String-literal paths must include their own quotes,
     * e.g. "'$.tags'". Empty (default) means the whole document.
     */
    String path() default "";
}

package com.javaoffers.brief.modelhelper.anno.fun.params.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON_EXTRACT(colName, path[, path]...) : Extracts the value(s) at the given path(s)
 * from a JSON document. String-literal paths must include their own quotes.
 * <p>
 *   @ColName("extra_info") @JsonExtract("'$.nickName'")
 *   // → JSON_EXTRACT(extra_info, '$.nickName')
 * <p>
 *   @ColName("extra_info") @JsonExtract({"'$.nickName'", "'$.age'"})
 *   // → JSON_EXTRACT(extra_info, '$.nickName', '$.age')
 * <p>
 * Commonly stacked with {@link JsonUnquote} to obtain a clean scalar:
 *   @JsonExtract("'$.nickName'") @JsonUnquote
 *   // → JSON_UNQUOTE(JSON_EXTRACT(colName, '$.nickName'))
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonExtract {

    public static final String TAG = "JSON_EXTRACT";

    /**
     * JSON path(s) to extract. String-literal paths must include their own quotes,
     * e.g. "'$.name'", "'$.user.age'".
     */
    String[] value();
}

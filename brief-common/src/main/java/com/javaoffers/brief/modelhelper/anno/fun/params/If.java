package com.javaoffers.brief.modelhelper.anno.fun.params;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * if(colName, expr1, expr2). Usually used with @ColName
 * sample:
 * <p>
 *     @ColName("sex = 1")
 *     @If(ep1 = "boy",ep2 = "girl")
 *     private String colName12; // IF(sex = 1,boy,girl)
 * <p/>
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface If {

    public static final String TAG = "IF";

    /**
     * expr1
     */
    String ep1();

    /**
     * expr2
     */
    String ep2();
}

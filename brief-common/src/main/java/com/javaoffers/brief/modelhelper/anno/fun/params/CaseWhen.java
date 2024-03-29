package com.javaoffers.brief.modelhelper.anno.fun.params;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * case when xx then xx
 *      when xx then xx
 *      else xxx end
 * <p>
 * sample:
 *
 *    @CaseWhen()
 *    private String scoreDescription;
 *
 *
 * </p>
 *
 *
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CaseWhen {

    public static final String CASE = "(CASE";
    public static final String WHEN = " WHEN ";
    public static final String THEN = " THEN ";
    public static final String ELSE = " ELSE ";
    public static final String END =  " END)";

    /**
     * when epx then xxx ....
     */
    When[] whens();

    /**
     * else xxx end
     */
    Else elseEnd() ;

    /**
     * when ep1 then xxx
     */
    public static @interface When {
        /**
         * when ep1
         */
        String when();

        /**
         * then xxx
         */
        String then();
    }

    public static @interface Else {
        String value();
    }


}


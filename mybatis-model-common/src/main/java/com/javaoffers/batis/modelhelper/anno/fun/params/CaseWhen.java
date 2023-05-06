package com.javaoffers.batis.modelhelper.anno.fun.params;

import com.javaoffers.batis.modelhelper.anno.ColName;

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
        String ep1();

        /**
         * then xxx
         */
        String then();
    }

    public static @interface Else {
        String value();
    }


}


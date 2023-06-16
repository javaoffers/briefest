package com.javaoffers.brief.modelhelper.anno.derive;

import com.javaoffers.brief.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * String desensitization.
 * Treat the data as an ordinary string for fuzzing. Some features will not be preserved. For example,
 * email will not retain the @xxx part if this fuzzing is used.
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface StringBlur {

    /**
     * Blur percentage. Default is 0.3.
     */
    double percent() default ModelHelpperConstants.BLUR_PERCENT;

    /**
     * Blur strategy, the default is intermediate blur.
     */
    Blur blur() default Blur.MIDDLE_BLUR;

    /**
     * default fuzzy symbol
     */
    String blurTag() default  ModelHelpperConstants.BLUR_TAG;
}

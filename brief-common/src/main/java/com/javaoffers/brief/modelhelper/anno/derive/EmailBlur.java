package com.javaoffers.brief.modelhelper.anno.derive;

import com.javaoffers.brief.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * email number desensitization.
 * The content of @xxxx will be kept.
 * For example, 123456789@outlook.com.
 * The result after blurring is: 123***789@outlook.com
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EmailBlur {

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

package com.javaoffers.batis.modelhelper.anno.fun.params.time;

import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;

/**
 * @author mingJie
 */
public @interface Week {
    public static final String TAG = "WEEK";
    /**
     * The table field corresponding to field. The default is filed name underscore format
     * You can also modify the value of value to change the table field corresponding to field. It is the modified value.
     * Functions like {@link com.javaoffers.batis.modelhelper.anno.ColName}.
     * Note: When using {@code @ColName}. on field to specify table field information. value here will be ignored
     * (even if value has been modified).
     */
    String value() default ModelHelpperConstants.FIELD_COL_NAME;
}

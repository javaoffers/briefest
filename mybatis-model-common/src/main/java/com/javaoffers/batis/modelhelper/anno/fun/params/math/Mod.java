package com.javaoffers.batis.modelhelper.anno.fun.params.math;

import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MOD(colName, ep1)
 * SELECT MOD(234, 10); # 4
 * @author mingJie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Mod {
    public static final String TAG = "MOD";
    int ep1();
}

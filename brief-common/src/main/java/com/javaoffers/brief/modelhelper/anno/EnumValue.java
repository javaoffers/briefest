package com.javaoffers.brief.modelhelper.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: support enum convert.
 * {@code EnumValue} cannot be in the same enumeration class on multiple attributes used at the same time.
 * Only mark in one of the attributes and have unique characteristics.No @EnumValue attribute will be ordinal
 * as default values.
 * @author: create by cmj on 2023/1/2 11:46
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EnumValue {

}

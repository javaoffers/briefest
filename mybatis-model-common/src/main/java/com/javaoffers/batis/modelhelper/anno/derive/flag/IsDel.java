package com.javaoffers.batis.modelhelper.anno.derive.flag;

import com.javaoffers.batis.modelhelper.anno.EnumValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: Whether or not to be deleted
 * @author: create by cmj on 2023/6/11 01:39
 */
public enum IsDel {
    //Data is logically deleted
    YES(1),
    //data is valid, there is not be logically deleted
    NO(0),
    ;
    @EnumValue
    private int code;

    IsDel(int code){
        this.code = code;
    }

}

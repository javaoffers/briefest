package com.javaoffers.batis.modelhelper.anno.derive;

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
    //Have deleted
    YES(0),
    //Not be deleted
    NO(1),
    ;
    @EnumValue
    private int code;

    IsDel(int code){
        this.code = code;
    }

}

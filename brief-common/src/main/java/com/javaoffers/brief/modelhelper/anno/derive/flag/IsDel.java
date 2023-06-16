package com.javaoffers.brief.modelhelper.anno.derive.flag;

import com.javaoffers.brief.modelhelper.anno.EnumValue;

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

package com.javaoffers.base.modelhelper.sample.spring.constant;

import com.javaoffers.brief.modelhelper.anno.EnumValue;

/**
 * @description:
 * @author: create by cmj on 2023/1/2 16:10
 */
public enum Work {

    JAVA(1,"JAVA"),
    PYTHON(2,"PYTHON")
    ;
    private int code;
    @EnumValue
    private String workName;

    Work(int code, String workName){
        this.code = code;
        this.workName = workName;
    }
}

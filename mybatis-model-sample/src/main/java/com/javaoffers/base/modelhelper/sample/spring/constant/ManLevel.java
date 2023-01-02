package com.javaoffers.base.modelhelper.sample.spring.constant;

import com.javaoffers.batis.modelhelper.anno.EnumValue;

/**
 * @description:
 * @author: create by cmj on 2023/1/2 14:41
 */
public enum ManLevel {
    RICH(10,"rich"),
    POOR(11,"poor")
    ;
    private int code;
    @EnumValue
    private String mes;
    ManLevel(int code, String mes){
        this.code = code;
        this.mes = mes;
    }
}

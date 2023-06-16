package com.javaoffers.brief.modelhelper.anno.derive.flag;

import com.javaoffers.brief.modelhelper.anno.EnumValue;

/**
 *  Whether or not to be deleted
 * @author mingJie
 */
public enum RowStatus {

    /**
     * Data is logically deleted
     */
    ABSENT(0),

    /**
     * data is valid, there is not be logically deleted
     */
    PRESENCE(1)

    ;
    @EnumValue
    private int code;

    RowStatus(int code){
        this.code = code;
    }
}

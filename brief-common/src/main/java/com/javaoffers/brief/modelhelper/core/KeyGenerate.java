package com.javaoffers.brief.modelhelper.core;

/**
 * @author mingJie
 */
public enum KeyGenerate {

    //无效key. 由服务自己决定.
    VOID_KEY,

    //自增, 长度31位, nowTime(固定不变12位) + increment(原子自增 19位.)
    ATO_INCREMENT,

    //UUID
    UUID,

    //雪花, 可用于分布式id
    SNOWFLAKE,

    ;

}

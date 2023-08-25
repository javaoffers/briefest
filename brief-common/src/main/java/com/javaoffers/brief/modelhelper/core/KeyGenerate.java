package com.javaoffers.brief.modelhelper.core;

/**
 * @author mingJie
 */
public enum KeyGenerate {

    //无效key. 由服务自己决定.
    VOID_KEY,

    //自增, {@link AtoIncrementSequenceKey}
    ATO_INCREMENT_SEQ27,

    //UUID
    UUID,

    //雪花, 可用于分布式id
    SNOWFLAKE,

    ;

}

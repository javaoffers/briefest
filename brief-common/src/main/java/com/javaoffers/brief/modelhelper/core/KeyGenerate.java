package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.core.gkey.AtoIncrementSequenceKey;
import com.javaoffers.brief.modelhelper.core.gkey.SnowflakeKey;
import com.javaoffers.brief.modelhelper.core.gkey.UUIDKey;
import com.javaoffers.brief.modelhelper.core.gkey.VoidKey;

/**
 * @author mingJie
 */
public enum KeyGenerate {

    //无效key. 由服务自己决定.
    VOID_KEY(VoidKey.class),

    //自增, {@link AtoIncrementSequenceKey}
    ATO_INCREMENT_SEQ27(AtoIncrementSequenceKey.class),

    //UUID
    UUID(UUIDKey.class),

    //雪花, 可用于分布式id
    SNOWFLAKE(SnowflakeKey.class),

    ;

    private Class<UniqueKeyGenerate> gkeyClass;

    KeyGenerate(Class gkeyClass){
        this.gkeyClass = gkeyClass;
    }

    public Class getGkeyClass() {
        return gkeyClass;
    }
}

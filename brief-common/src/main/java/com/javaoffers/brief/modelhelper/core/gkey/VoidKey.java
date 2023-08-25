package com.javaoffers.brief.modelhelper.core.gkey;

import com.javaoffers.brief.modelhelper.core.UniqueKeyGenerate;

/**
 * @author mingJie
 */
public class VoidKey implements UniqueKeyGenerate {
    @Override
    public Object generate() {
        throw new UnsupportedOperationException("UniqueKeyGenerate VoidKey");
    }
}

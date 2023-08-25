package com.javaoffers.brief.modelhelper.core;

/**
 * @author mingJie
 */
public class VoidKey implements UniqueKeyGenerate {
    @Override
    public Object generate() {
        throw new UnsupportedOperationException("UniqueKeyGenerate VoidKey");
    }
}

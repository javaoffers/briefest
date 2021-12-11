package com.javaoffers.base.batis.core;

import com.javaoffers.base.batis.convert.Convert;

/**
 * 注册器
 */
public interface Register<D extends Descriptor,C extends Convert> {
    /**
     * 注册选择器
     * @param descriptor
     */
    public void registerConvert(D descriptor, C convert);
}

package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.convert.Convert;

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

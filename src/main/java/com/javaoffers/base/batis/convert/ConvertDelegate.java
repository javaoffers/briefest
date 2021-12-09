package com.javaoffers.base.batis.convert;

import com.javaoffers.base.batis.core.ConvertSelector;
import com.javaoffers.base.batis.core.Descriptor;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/8 13:17
 */
public class ConvertDelegate<T extends Descriptor,V> implements Convert<T,V> {

    ConvertSelector convertSelector;


    @Override
    public V convert(T o) {
        Convert convert = convertSelector.selector(o);
        return (V) convert.convert(o);
    }
}

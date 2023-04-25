package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.convert.Convert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/7 20:12
 */
public class  ModelApplicationContext implements SelectorRegister {


    Map<String, Convert> registers = new ConcurrentHashMap<>();

    @Override
    public void registerConvert(Descriptor descriptor, Convert convert) {
        registers.putIfAbsent(descriptor.getUniqueMark(),convert);
    }


    @Override
    public Convert selector(Descriptor descriptor) {
        return registers.get(descriptor.getUniqueMark());
    }
}

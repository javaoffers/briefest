package com.javaoffers.base.batis.core;

import com.javaoffers.base.batis.convert.Convert;
import com.javaoffers.base.batis.util.ReflectionUtils;

import java.util.Set;

/**
 * @Description: Selector represents and selector uses entry
 * @Auther: create by cmj on 2021/12/9 10:52
 */
public class ConvertSelectorDelegate {

    ConvertSelector convertSelector = new GenericConvertSelector();

    {
        Set<Class<? extends Convert>> converts = ReflectionUtils.getChildOfConvert();
        for(Class c : converts){

        }
    }

    /**
     * registerConvert
     * @param src
     * @param des
     * @param convert
     */
    public void registerConvert(Class src,Class des, Convert convert) {
        convertSelector.registerConvert(new ConverDescriptor(src,des),convert);
    }

    /**
     * selector
     * @return
     */
    public Convert selector(Class src,Class des) {
        return convertSelector.selector(new ConverDescriptor(src,des));
    }

    /**
     * 类型转换
     * @param type 要转换的目标类型
     * @param srcValue 原始值类型
     * @param <T>
     * @return
     */
    public <T> T converterObject(Class<T> type, Object srcValue) throws Exception{
        //选取 convert
        Convert selector = convertSelector.selector(new ConverDescriptor(srcValue.getClass(), type));

        //开始转换
        if(selector ==null){
            throw new ClassCastException("primitive type:" +srcValue.getClass().getName()+" target type: "+type.getName()+" nonexistent");
        }
        return (T) selector.convert(srcValue);
    }



}
